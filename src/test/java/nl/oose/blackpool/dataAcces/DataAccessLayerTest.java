package nl.oose.blackpool.dataAcces;


import nl.oose.blackpool.DTO.CreateChildAndAddToGroupRequest;
import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.DTO.LoginDTO;
import nl.oose.blackpool.domain.Child;
import nl.oose.blackpool.domain.Group;
import nl.oose.blackpool.domain.IdentificationImage;
import nl.oose.blackpool.domain.Permissions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import nl.oose.blackpool.domain.User;

@RunWith(Enclosed.class)
public class DataAccessLayerTest {
    /*

     */


    @RunWith(MockitoJUnitRunner.class)
    public static class PermissionsDaoTest {

        @Mock
        private DbConnector dbConnectorMock = mock(DbConnector.class);


        @InjectMocks
        private PermissionsDaoImpl sut = new PermissionsDaoImpl();

        private DbTestConnector dbTestConnector = new DbTestConnector();
        private Permissions permissions1;
        private Permissions permissions2;

        public PermissionsDaoTest() throws IOException, ClassNotFoundException {

        }


        @Before
        public void setup() throws SQLException {
            permissions1 = new Permissions(true, true, true, 1);
            permissions2 = new Permissions(false, false, false, 2);
            sut.setDbConnector(dbConnectorMock);
            dbTestConnector.reloadTestDatabase();
        }


        @Test
        public void getShouldReturnExpected() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            Optional<Permissions> optionalActualPermissions = sut.get(1);
            Permissions actualPermissions;
            if (optionalActualPermissions.isPresent()) {
                actualPermissions = optionalActualPermissions.get();
            } else {
                actualPermissions = new Permissions(false, false, false, 0);
            }

            //Assert
            Assert.assertEquals(permissions1.getChildId(), actualPermissions.getChildId());
        }

        @Test
        public void getShouldReturnEmptyOptional() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());
            Optional<Permissions> expectedToBeEmptyOptional;

            //Act
            expectedToBeEmptyOptional = sut.get(4);

            //Assert
            Assert.assertFalse(expectedToBeEmptyOptional.isPresent());
        }

        @Test(expected = DataException.class)
        public void getShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.get(5);
        }


        @Test
        public void getAllShouldReturnExpected() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());
            List<Permissions> expectedPermissionsList = new ArrayList<>();
            expectedPermissionsList.add(permissions1);
            expectedPermissionsList.add(permissions2);

            //Act
            List<Permissions> actualPermissionsList = sut.getAll();

            //Assert
            Assert.assertEquals(expectedPermissionsList.size(), actualPermissionsList.size());
        }

        @Test(expected = DataException.class)
        public void getAllShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.getAll();
        }

        @Test
        public void saveShouldSave() throws SQLException, DataException {
            //Arrange
            AtomicBoolean testPassed = new AtomicBoolean(false);
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());
            Permissions permissionsToSave = new Permissions(true, false, true, 3);

            //Act
            sut.save(permissionsToSave);
            sut.get(permissionsToSave.getChildId()).ifPresent(Permissions -> {
                if (Permissions.getChildId() == permissionsToSave.getChildId()) {
                    testPassed.set(true);
                }
            });

            //Assert
            Assert.assertTrue(testPassed.get());

        }

        @Test(expected = DataException.class)
        public void saveShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.save(permissions1);
        }

        @Test
        public void updateShouldUpdate() throws SQLException, DataException {
            //Arrange
            AtomicBoolean testPassed = new AtomicBoolean(false);
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());
            Permissions permissionsToUpdate = permissions1;
            permissionsToUpdate.setSocialMediaPermission(false);

            //Act
            sut.update(permissionsToUpdate);
            sut.get(permissionsToUpdate.getChildId()).ifPresent(permissions -> {
                if (permissions.isSocialMediaPermission() == permissionsToUpdate.isSocialMediaPermission()) {
                    testPassed.set(true);
                }
            });

            //Assert
            Assert.assertTrue(testPassed.get());
        }

        @Test(expected = DataException.class)
        public void updateShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.update(permissions1);
        }

        @Test
        public void deleteShouldDelete() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());
            Permissions permissionsToDelete = permissions1;

            //Act
            sut.delete(permissionsToDelete.getChildId());

            //Assert
            Assert.assertFalse(sut.get(permissionsToDelete.getChildId()).isPresent());
        }

        @Test(expected = DataException.class)
        public void deleteShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.delete(permissions1.getChildId());
        }


    }

    @RunWith(MockitoJUnitRunner.class)
    public static class IdentificationImageDaoTest {
        @Mock
        DbConnector dbConnectormock = mock(DbConnector.class);

        @InjectMocks
        IdentificationImageDaoImpl sut;

        private DbTestConnector dbTestConnector = new DbTestConnector();
        private IdentificationImage identificationImage1;
        private IdentificationImage identificationImage2;

        public IdentificationImageDaoTest() throws IOException, ClassNotFoundException {
        }

        @Before
        public void setup() throws SQLException {
            identificationImage1 = new IdentificationImage(1, 1, "");
            identificationImage2 = new IdentificationImage(2, 2, "");
            dbTestConnector.reloadTestDatabase();
        }

        @Test
        public void getShouldReturnExpected() throws SQLException, DataException {
            //Arrange
            IdentificationImage expectedIdentificationImage = identificationImage1;
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            Optional<IdentificationImage> actualOptionalIdentificationImage;
            IdentificationImage actualIdentificationImage = null;
            actualOptionalIdentificationImage = sut.get(1);
            if (actualOptionalIdentificationImage.isPresent()) {
                actualIdentificationImage = actualOptionalIdentificationImage.get();
            }

            //Assert
            Assert.assertEquals(expectedIdentificationImage.getImageID(), actualIdentificationImage.getImageID());
        }

        @Test
        public void getShouldReturnEmptyOptional() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            Optional<IdentificationImage> expectedToBeEmptyOptional = sut.get(4);

            //Assert
            Assert.assertFalse(expectedToBeEmptyOptional.isPresent());
        }

        @Test(expected = DataException.class)
        public void getShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.get(4);
        }

        @Test
        public void getAllShouldReturnExpected() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());
            List<IdentificationImage> expectedIdentificationImages = new ArrayList<>();
            expectedIdentificationImages.add(identificationImage1);
            expectedIdentificationImages.add(identificationImage2);

            //Act
            List<IdentificationImage> actualIdentificationImages = sut.getAll();

            //Assert
            Assert.assertEquals(expectedIdentificationImages.size(), actualIdentificationImages.size());
        }

        @Test(expected = DataException.class)
        public void getAllShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.get(4);
        }

        @Test
        public void getAllByIdShouldReturnExpected() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());
            List<IdentificationImage> expectedIdentificationImages = new ArrayList<>();
            expectedIdentificationImages.add(identificationImage1);
            AtomicReference<List<IdentificationImage>> actualIdentificationImages = new AtomicReference<>();

            //Act
            sut.getAllByChildId(1).ifPresent(actualIdentificationImages::set);

            //Assert
            Assert.assertEquals(expectedIdentificationImages.size(), actualIdentificationImages.get().size());
        }

        @Test
        public void getAllByIdShouldReturnEmptyOptional() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());
            Optional<List<IdentificationImage>> expectedToBeEmptyOptional;

            //Act
            expectedToBeEmptyOptional = sut.getAllByChildId(4);

            //Assert
            Assert.assertFalse(expectedToBeEmptyOptional.isPresent());
        }

        @Test(expected = DataException.class)
        public void getAllByIdShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.getAll();
        }

        @Test
        public void saveShouldSave() throws SQLException, DataException {
            //Arrange
            AtomicBoolean testPassed = new AtomicBoolean(false);
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());
            IdentificationImage identificationImageToSave = new IdentificationImage(1, 3, "aPath");

            //Act
            sut.save(identificationImageToSave);
            if(sut.getAll().size() > 2) {
                testPassed.set(true);
            }
            //Assert
            Assert.assertTrue(testPassed.get());
        }

        @Test(expected = DataException.class)
        public void saveShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.save(identificationImage1);
        }

        @Test
        public void updateShouldUpdate() throws SQLException, DataException {
            //Arrange
            AtomicBoolean testPassed = new AtomicBoolean(false);
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());
            IdentificationImage identificationImageToUpdate = identificationImage1;
            identificationImageToUpdate.setPathToImage("aNewPath");

            //Act
            sut.update(identificationImageToUpdate);
            sut.get(identificationImageToUpdate.getImageID()).ifPresent(identificationImage -> {
                if (identificationImage.getPathToImage().equals(identificationImageToUpdate.getPathToImage())) {
                    testPassed.set(true);
                }
            });

            //Assert
            Assert.assertTrue(testPassed.get());
        }

        @Test(expected = DataException.class)
        public void updateShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.update(identificationImage1);
        }

        @Test
        public void deleteShouldDelete() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());
            IdentificationImage identificationImageToDelete = identificationImage1;

            //Act
            sut.delete(identificationImageToDelete.getImageID());

            //Assert
            Assert.assertFalse(sut.get(identificationImageToDelete.getChildId()).isPresent());
        }

        @Test(expected = DataException.class)
        public void deleteShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.delete(identificationImage1.getImageID());
        }
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class ChildDaoTest {
        @Mock
        DbConnector dbConnectorMock = mock(DbConnector.class);
        PermissionsDaoImpl permissionsDaoMock = mock(PermissionsDaoImpl.class);
        IdentificationImageDaoImpl identificationImageImplMock = mock(IdentificationImageDaoImpl.class);

        @InjectMocks
        ChildDaoImpl sut;

        private DbTestConnector dbTestConnector = new DbTestConnector();
        private Child child1;
        private Child child2;
        private Child child3;
        private Permissions permissions;
        private List<IdentificationImage> identificationImages = new ArrayList<>();

        @Before
        public void setup() throws SQLException {
            child1 = new Child(1, "Willem", "Weenink");
            child2 = new Child(2, "Stijn", "Heijden");
            child3 = new Child(3, "Jan Hendrik", "Klok");

            permissions = new Permissions(false, false, false, 1);
            identificationImages.add(new IdentificationImage(1, 1, ""));

            dbTestConnector.reloadTestDatabase();
        }

        public ChildDaoTest() throws IOException, ClassNotFoundException {
        }

        @Test
        public void getShouldReturnExpected() throws SQLException, DataException {
            //Arrange
            Child expectedChild = child1;
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());
            when(permissionsDaoMock.get(child1.getChildId())).thenReturn(Optional.of(permissions));
            when(identificationImageImplMock.getAllByChildId(child1.getChildId())).thenReturn(Optional.of(identificationImages));

            //Act
            Optional<Child> actualOptionalChild;
            Child actualChild = null;
            actualOptionalChild = sut.get(1);
            if (actualOptionalChild.isPresent()) {
                actualChild = actualOptionalChild.get();
            }

            //Assert
            Assert.assertEquals(expectedChild.getChildId(), actualChild.getChildId());
        }

        @Test
        public void getShouldReturnEmptyOptional() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            Optional<Child> expectedToBeEmptyOptional = sut.get(4);

            //Assert
            Assert.assertFalse(expectedToBeEmptyOptional.isPresent());
        }

        @Test(expected = DataException.class)
        public void getShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.get(10);
        }

        @Test
        public void getAllShouldReturnExpected() throws SQLException, DataException {
            //Arrange
            Child expectedChild1 = child1;
            Child expectedChild2 = child2;
            Child expectedChild3 = child3;
            List<Child> listOfExpectedChildren = new ArrayList<>();
            listOfExpectedChildren.add(expectedChild1);
            listOfExpectedChildren.add(expectedChild2);
            listOfExpectedChildren.add(expectedChild3);
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());
            when(permissionsDaoMock.get(expectedChild1.getChildId())).thenReturn(Optional.of(permissions));
            when(identificationImageImplMock.getAllByChildId(expectedChild1.getChildId())).thenReturn(Optional.of(identificationImages));
            when(permissionsDaoMock.get(expectedChild2.getChildId())).thenReturn(Optional.of(permissions));
            when(identificationImageImplMock.getAllByChildId(expectedChild2.getChildId())).thenReturn(Optional.of(identificationImages));

            //Act
            List<Child> listOfActualChildren = sut.getAll();

            //Assert
            Assert.assertEquals(listOfExpectedChildren.size(), listOfActualChildren.size());
        }

        @Test(expected = DataException.class)
        public void getAllShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.getAll();
        }

        @Test
        public void saveShouldSave() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());
            Child childToSave = new Child(3, "Frits", "Flipper");

            //Act
            sut.save(childToSave);

            //Assert
            Assert.assertTrue(sut.get(childToSave.getChildId()).isPresent());
        }

        @Test(expected = DataException.class)
        public void saveShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.save(child1);
        }

        @Test
        public void updateShouldUpdate() throws SQLException, DataException {
            //Arrange
            AtomicBoolean testPassed = new AtomicBoolean(false);
            Child childToUpdate = child1;
            childToUpdate.setFirstName("Jan");
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());
            when(permissionsDaoMock.get(childToUpdate.getChildId())).thenReturn(Optional.of(permissions));
            when(identificationImageImplMock.getAllByChildId(childToUpdate.getChildId())).thenReturn(Optional.of(identificationImages));

            //Act
            sut.update(childToUpdate);
            sut.get(childToUpdate.getChildId()).ifPresent(child -> {
                if (child.getFirstName().equals(childToUpdate.getFirstName())) {
                    testPassed.set(true);
                }
            });

            //Assert
            Assert.assertTrue(testPassed.get());
        }

        @Test(expected = DataException.class)
        public void updateShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.update(child1);
        }

        @Test
        public void deleteShouldDelete() throws SQLException, DataException {
            //Arrange
            Child childToDelete = child1;
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            sut.delete(childToDelete.getChildId());

            //Assert
            Assert.assertFalse(sut.get(childToDelete.getChildId()).isPresent());
        }

        @Test(expected = DataException.class)
        public void deleteShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.delete(child1.getChildId());
        }

        @Test
        public void getByClassIdShouldReturnExpected() throws SQLException, DataException {
            //Arrange
            Child expectedChild = child1;
            List<Child> listOfExpectedChildren = new ArrayList<>();
            listOfExpectedChildren.add(expectedChild);
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());
            when(permissionsDaoMock.get(expectedChild.getChildId())).thenReturn(Optional.of(permissions));
            when(identificationImageImplMock.getAllByChildId(expectedChild.getChildId())).thenReturn(Optional.of(identificationImages));
            AtomicReference<List<Child>> actualChildren = new AtomicReference<>();

            //Act
            sut.getByGroupId(1).ifPresent(actualChildren::set);

            //Assert
            Assert.assertEquals(listOfExpectedChildren.size(), actualChildren.get().size());
        }

        @Test
        public void getByClassIdShouldReturnEmptyOptional() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            Optional<List<Child>> expectedToBeEmptyOptional = sut.getByGroupId(10);

            //Assert
            Assert.assertFalse(expectedToBeEmptyOptional.isPresent());
        }

        @Test(expected = DataException.class)
        public void getByClassIdShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.getByGroupId(10);
        }

        @Test
        public void addToGroupShouldAddChildToGroup() throws SQLException, DataException {
            //Arrange
            Child childToAdd = child3;
            List<Child> expectedChildren = new ArrayList<>();
            AtomicReference<List<Child>> actualChildren = new AtomicReference<>();
            expectedChildren.add(child1);
            expectedChildren.add(childToAdd);
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            sut.addToGroup(1, childToAdd);
            sut.getByGroupId(1).ifPresent(actualChildren::set);

            //Assert
            Assert.assertEquals(expectedChildren.size(), actualChildren.get().size());
        }

        @Test(expected = DataException.class)
        public void addToGroupShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.addToGroup(10, child1);
        }

        @Test
        public void removeFromGroupShouldRemoveChildFromGroup() throws SQLException, DataException {
            Child childToRemove = child3;
            List<Child> expectedChildren = new ArrayList<>();
            AtomicReference<List<Child>> actualChildren = new AtomicReference<>();
            expectedChildren.add(child1);
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            sut.removeFromGroup(1, childToRemove);
            sut.getByGroupId(1).ifPresent(actualChildren::set);

            //Assert
            Assert.assertEquals(expectedChildren.size(), actualChildren.get().size());
        }

        @Test(expected = DataException.class)
        public void removeFromGroupShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.removeFromGroup(10, child1);
        }

        @Test
        public void createAndAddToGroupShouldSave() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());
            int groupToSaveTo = 1;
            Child childToSave = new Child(3, "Frits", "Flipper");

            //Act
            sut.createAndAddToGroup(groupToSaveTo,childToSave);

            //Assert
            Assert.assertTrue(sut.getAll().size() > 2);
        }

        @Test(expected = DataException.class)
        public void createAndAddToGroupShouldException() throws SQLException, DataException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenThrow(new SQLException());
            int groupToSaveTo = 1;
            Child childToSave = new Child(3, "Frits", "Flipper");

            //Act
            sut.createAndAddToGroup(groupToSaveTo, childToSave);
        }
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class LoginDaoTest {

        @Mock
        private DbConnector dbConnectorMock = mock(DbConnector.class);

        @InjectMocks
        private LoginDaoImpl sut = new LoginDaoImpl();

        private DbTestConnector dbTestConnector = new DbTestConnector();
        private User user;

        public LoginDaoTest() throws IOException, ClassNotFoundException {
        }

        @Before
        public void setup() throws SQLException {
            user = new User(1, "TestUser");

            sut.setDbConnector(dbConnectorMock);
            dbTestConnector.reloadTestDatabase();
        }



        @Test
        public void getShouldReturnExpected() throws SQLException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            Optional<User> optionalActualUser = sut.get(1);
            User actualUser;
            if (optionalActualUser.isPresent()) {
                actualUser = optionalActualUser.get();
            } else {
                actualUser = new User(1, "TestUser");
            }

            //Assert
            Assert.assertEquals(user.getId(), actualUser.getId());
        }

        @Test
        public void getShouldReturnEmptyOptional() throws SQLException {
            //Arrange
            when(dbConnectorMock.getConnection()).thenReturn(dbTestConnector.getConnection());
            Optional<User> expectedToBeEmptyOptional;
            //Act
            expectedToBeEmptyOptional = sut.get(2);

            //Assert
            Assert.assertFalse(expectedToBeEmptyOptional.isPresent());
        }
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class GroupDaoTest {
        @Mock
        DbConnector dbConnectormock = mock(DbConnector.class);
        ChildDaoImpl childDaomock = mock(ChildDaoImpl.class);

        @InjectMocks
        GroupDaoImpl sut;

        private DbTestConnector dbTestConnector = new DbTestConnector();
        private Group group1;
        private Group group2;
        private Child child;

        @Before
        public void setup() throws SQLException {
            child = new Child(1, "Willem", "Weenink");
            group1 = new Group(1, "Groep1");
            group2 = new Group(2, "Groep2");
            dbTestConnector.reloadTestDatabase();
        }

        public GroupDaoTest() throws IOException, ClassNotFoundException {
        }

        @Test
        public void getShouldReturnExpected() throws SQLException, DataException {
            //Arrange
            Group expectedGroup = group1;
            List<Child> exectedChild = new ArrayList<>();
            exectedChild.add(child);
            AtomicReference<Group> actualGroup = new AtomicReference<>();
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());
            when(childDaomock.getByGroupId(expectedGroup.getGroupId())).thenReturn(Optional.of(exectedChild));

            //Act
            sut.get(1).ifPresent(actualGroup::set);

            //Assert
            Assert.assertEquals(expectedGroup.getGroupId(), actualGroup.get().getGroupId());
        }

        @Test
        public void getShouldReturnEmptyOptional() throws SQLException, DataException {
            //Arrange
            Optional<Group> actualOptional;
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            actualOptional = sut.get(10);

            //Assert
            Assert.assertFalse(actualOptional.isPresent());
        }

        @Test(expected = DataException.class)
        public void getShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.get(10);
        }

        @Test
        public void getAllShouldReturnExpected() throws SQLException, DataException {
            //Arrange
            List<Group> expectedGroups = new ArrayList<>();
            List<Group> actualGroup = new ArrayList<>();
            List<Child> expectedChildren = new ArrayList<>();
            expectedChildren.add(child);
            expectedGroups.add(group1);
            expectedGroups.add(group2);
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());
            when(childDaomock.getByGroupId(group1.getGroupId())).thenReturn(Optional.of(expectedChildren));
            when(childDaomock.getByGroupId(group2.getGroupId())).thenReturn(Optional.of(expectedChildren));

            //Act
            actualGroup = sut.getAll();

            //Assert
            Assert.assertEquals(expectedGroups.size(), actualGroup.size());
        }

        @Test(expected = DataException.class)
        public void getAllShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.getAll();
        }

        @Test
        public void saveShouldSave() throws SQLException, DataException {
            //Arrange
            Group groupToSave = new Group(3, "Groep3");
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            sut.save(groupToSave);

            //Assert
            Assert.assertTrue(sut.getAll().size() > 2);
        }

        @Test(expected = DataException.class)
        public void saveShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.save(group1);
        }

        @Test
        public void updateShouldUpdate() throws SQLException, DataException {
            //Arrange
            AtomicBoolean testPassed = new AtomicBoolean(false);
            Group groupToUpdate = group1;
            groupToUpdate.setGroupName("groepje1");
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            sut.update(groupToUpdate);
            sut.get(groupToUpdate.getGroupId()).ifPresent(group -> {
                if (group.getGroupName().equals(groupToUpdate.getGroupName())) {
                    testPassed.set(true);
                }
            });

            //Assert
            Assert.assertTrue(testPassed.get());
        }

        @Test(expected = DataException.class)
        public void updateShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.update(group1);
        }

        @Test
        public void deleteShouldDelete() throws SQLException, DataException {
            //Arrange
            Group groupToDelete = group1;
            when(dbConnectormock.getConnection()).thenReturn(dbTestConnector.getConnection());

            //Act
            sut.delete(groupToDelete.getGroupId());

            //Assert
            Assert.assertFalse(sut.get(groupToDelete.getGroupId()).isPresent());
        }

        @Test(expected = DataException.class)
        public void deleteShouldThrowException() throws SQLException, DataException {
            //Arrange
            when(dbConnectormock.getConnection()).thenThrow(new SQLException());

            //Act
            sut.delete(group1.getGroupId());
        }
    }
}
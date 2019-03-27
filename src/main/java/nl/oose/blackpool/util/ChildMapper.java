package nl.oose.blackpool.util;

import nl.oose.blackpool.DTO.ChildDTO;
import nl.oose.blackpool.domain.Child;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ChildMapper extends Mapper<Child, ChildDTO> {

    private PermissionsMapper permissionsMapper;

    @Override
    public ChildDTO domainToDTO(Child child) {
        ChildDTO childDTOToReturn = new ChildDTO();
        childDTOToReturn.setFirstName(child.getFirstName());
        childDTOToReturn.setLastName(child.getLastName());
        childDTOToReturn.setId(child.getChildId());
        if(child.getPermissions()!= null) {
            childDTOToReturn.setPermissions(permissionsMapper.domainToDTO(child.getPermissions()));
        }
        return childDTOToReturn;
    }

    @Override
    public Child DTOToDomain(ChildDTO childDTO) {
        Child childToReturn = new Child();
        childToReturn.setFirstName(childDTO.getFirstName());
        childToReturn.setLastName(childDTO.getLastName());
        childToReturn.setChildId(childDTO.getId());
        return childToReturn;
    }

    @Override
    public List<ChildDTO> domainToDTO(List<Child> t) {
        List<ChildDTO> childDTOListToReturn = new ArrayList<>();
        for(Child child:t) {
            childDTOListToReturn.add(domainToDTO(child));
        }
        return childDTOListToReturn;
    }

    @Override
    public List<Child> DTOToDomain(List<ChildDTO> childDTOS) {
        List<Child> childrenToReturn = new ArrayList<>();
        for(ChildDTO childDTO: childDTOS) {
            childrenToReturn.add(DTOToDomain(childDTO));
        }
        return childrenToReturn;
    }

    @Inject
    public void setPermissionsMapper(PermissionsMapper permissionsMapper) {
        this.permissionsMapper = permissionsMapper;
    }
}

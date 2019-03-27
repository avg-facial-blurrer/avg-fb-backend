package nl.oose.blackpool.util;

import java.util.List;

public abstract class Mapper<T,S> {
    public abstract S domainToDTO(T t);

    public abstract T DTOToDomain(S s);

    public abstract List<S> domainToDTO(List<T> t);

    public abstract List<T> DTOToDomain(List<S> s);
}

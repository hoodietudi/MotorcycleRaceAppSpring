package domain.validators;

public interface IValidator<T> {

    /**
     * validates an entity
     * @param entity T
     * @throws ValidationException Exception
     */
    void validate(T entity) throws ValidationException;
}

namespace lab_7.Model.Validator
{
    interface IValidator<E>
    {
        void Validate(E e);
    }
}

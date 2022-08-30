using System;

namespace MotorcycleContest.Domain.Dtos
{
    [Serializable]
    public class UserDto
    {
        public String Username { get; set; }
        public String Password { get; set; }

        public UserDto(String username, String password)
        {
            Username = username;
            Password = password;
        }
    }
}
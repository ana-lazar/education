using System;

namespace MotorcycleContest.Domain.Entities
{
    public class User : Entity<int>
    {
        public String Name { get; set; }
        public String Username { get; set; }
        public String Password { get; set; }

        public User()
        {
            this.Name = null;
            this.Username = null;
            this.Password = null;
        }

        public User(String name, String username, String password)
        {
            this.Name = name;
            this.Username = username;
            this.Password = password;
        }

        public override string ToString()
        {
            return "User " + ID + ", name " + Name + ", username " + Username + ", password " + Password + "; ";
        }
    }
}

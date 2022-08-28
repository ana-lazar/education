using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace motorcycle_rest_client
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            RunAsync().Wait();
        }

        static async Task RunAsync()
        {
            RaceRestClient client = new RaceRestClient("http://localhost:8080/races");
            await GetAllRaces(client);
            // await GetById(client);
            // await CreateRace(client);
            // await UpdateRace(client);
            // await DeleteRace(client);
        }
        
        static async Task GetAllRaces(RaceRestClient client)
        {
            try
            {
                Console.Out.WriteLine("Get all:");
                List<Race> races = await client.GetAll();
                races.ForEach(race => Console.Out.WriteLine(race.ToString()));
            }
            catch (ServiceException exception)
            {
                Console.Out.WriteLine(exception.Message);
            }
        }

        static async Task GetById(RaceRestClient client)
        {
            try
            {
                Console.Out.WriteLine("Get by id:");
                Race race = await client.GetById(2);
                Console.Out.WriteLine(race.ToString());
            }
            catch (ServiceException exception)
            {
                Console.Out.WriteLine(exception.Message);
            }
        }
        
        static async Task CreateRace(RaceRestClient client)
        {
            try
            {
                Console.Out.WriteLine("Create:");
                Race race = new Race(4, 0);
                Race added = await client.Create(race);
                Console.Out.WriteLine(added.ToString());
            }
            catch (ServiceException exception)
            {
                Console.Out.WriteLine(exception.Message);
            }
        }
        
        static async Task UpdateRace(RaceRestClient client)
        {
            try
            {
                Console.Out.WriteLine("Update:");
                Race race = new Race(15, 4);
                await client.Update(race);
                Console.Out.WriteLine("Updated race!");
            }
            catch (ServiceException exception)
            {
                Console.Out.WriteLine(exception.Message);
            }
        }
        
        static async Task DeleteRace(RaceRestClient client)
        {
            try
            {
                Console.Out.WriteLine("Delete:");
                await client.Delete(17);
                Console.Out.WriteLine("Deleted race!");
            }
            catch (ServiceException exception)
            {
                Console.Out.WriteLine(exception.Message);
            }
        }
    }
}

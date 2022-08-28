using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace motorcycle_rest_client
{
    public class RaceRestClient
    {
        private string URL;
        static HttpClient client = new HttpClient();

        public RaceRestClient(string url)
        {
            URL = url;
            client.BaseAddress = new Uri(URL);
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        }
        
        public async Task<List<Race>> GetAll()
        {
            List<Race> races;
            HttpResponseMessage response = await client.GetAsync(URL);
            if (response.IsSuccessStatusCode)
            {
                races = await response.Content.ReadAsAsync<List<Race>>();
            }
            else
            {
                throw new ServiceException("Couldn't get response: Response status is " + response.StatusCode);
            }
            return races;
        }
        
        public async Task<Race> GetById(int id)
        {
            Race race;
            string url = URL + '/' + id;
            HttpResponseMessage response = await client.GetAsync(url);
            if (response.IsSuccessStatusCode)
            {
                race = await response.Content.ReadAsAsync<Race>();
            }
            else
            {
                throw new ServiceException("Couldn't get response: Response status is " + response.StatusCode);
            }
            return race;
        }
        
        public async Task<Race> Create(Race race)
        {
            string json = JsonConvert.SerializeObject(race);
            StringContent data = new StringContent(json, Encoding.Unicode, "application/json");
            HttpResponseMessage response = await client.PostAsync(URL, data);
            Race added;
            if (response.IsSuccessStatusCode)
            {
                added =  await response.Content.ReadAsAsync<Race>();
            }
            else
            {
                throw new ServiceException("Couldn't get response: Response status is " + response.StatusCode);
            }
            return added;
        }
        
        public async Task Update(Race race)
        {
            string json = JsonConvert.SerializeObject(race);
            StringContent data = new StringContent(json, Encoding.Unicode, "application/json");
            string url = URL + '/' + race.Id;
            HttpResponseMessage response = await client.PutAsync(url, data);
            if (response.IsSuccessStatusCode)
            {
                await response.Content.ReadAsAsync<Race>();
            }
            else
            {
                throw new ServiceException("Couldn't get response: Response status is " + response.StatusCode);
            }
        }
        
        public async Task Delete(int id)
        {
            string url = URL + '/' + id;
            HttpResponseMessage response = await client.DeleteAsync(url);
            if (response.IsSuccessStatusCode)
            {
                await response.Content.ReadAsAsync<Race>();
            }
            else
            {
                throw new ServiceException("Couldn't get response: Response status is " + response.StatusCode);
            }
        }
    }
    
    class ServiceException : Exception
    {
        public ServiceException(string message) : base(message)
        {
        }
    }
}
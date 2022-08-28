using System.Collections;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;
using UnityEngine.Serialization;

public class GenerateAndMoveClouds : MonoBehaviour
{
    public GameObject cloudToSpread;
    public Material material;
    public int nrOfClouds = 2000;
    public float speed = -100f;
    public List<GameObject> clouds = new List<GameObject>();

    void Start()
    {
        for (int i = 0; i < nrOfClouds; i++)
        {
            SpreadCloud();
        }
    }

    private void SpreadCloud()
    {
        Vector3 ranPosition = new Vector3(Random.Range(-2000, 2000), 0, Random.Range(-2000, 2000));
        GameObject clone = Instantiate(cloudToSpread, ranPosition, Quaternion.identity);
        var size = Random.Range(10, 50);
        clone.gameObject.transform.localScale += new Vector3(size, size, size);
        clone.gameObject.transform.rotation = Quaternion.Euler(0, Random.Range(0, 180), 0);
        clone.gameObject.GetComponent<Renderer>().material = material;
        clouds.Add(clone.gameObject);
        
        ranPosition = new Vector3(Random.Range(-2000, 2000), 1000, Random.Range(-2000, 2000));
        clone = Instantiate(cloudToSpread, ranPosition, Quaternion.identity);
        size = Random.Range(10, 50);
        clone.gameObject.transform.localScale += new Vector3(size, size, size);
        clone.gameObject.transform.rotation = Quaternion.Euler(180, Random.Range(0, 180), 0);
        clone.gameObject.GetComponent<Renderer>().material = material;
        clouds.Add(clone.gameObject);

        var x = Random.Range(-2000, 2000);
        while (x < 500 && x > -222)
            x = Random.Range(-2000, 2000);
        ranPosition = new Vector3(x, 400, Random.Range(-2000, 2000));
        clone = Instantiate(cloudToSpread, ranPosition, Quaternion.identity);
        size = Random.Range(10, 50);
        clone.gameObject.transform.localScale += new Vector3(size, size, size);
        clone.gameObject.transform.rotation = Quaternion.Euler(0, Random.Range(0, 180), 0);
        clone.gameObject.GetComponent<Renderer>().material = material;
        clouds.Add(clone.gameObject);
    }
    
    void Update()
    {
        foreach (var cloud in clouds)
        {
            moveCloud(cloud);
        }
    }

    private void moveCloud(GameObject cloud)
    {
        var dt = Time.deltaTime;
        var position = cloud.transform.localPosition;
        position.z += speed * dt;
        if (position.z >= 2000)
            position.z = -1000;
        
        cloud.transform.localPosition = position;
    }
}
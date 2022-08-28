using System.Collections.Generic;
using UnityEngine;

public class GenerateAndMoveTreesScript : MonoBehaviour
{
    public GameObject treeToSpread;
    public GameObject treeToSpread1;
    public GameObject treeToSpread2;
    public int nrOfTrees = 2000;
    public float speed = -100f;
    public List<GameObject> trees = new();
        
    
    void Start()
    {
        for (int i = 0; i < nrOfTrees; i++)
        {
            SpreadTree();
        }
    }

    private void SpreadTree()
    {
        Vector3 ranPosition = new Vector3(Random.Range(-600, -2000), 0,
            Random.Range(-500, 4000));
        Vector3 ranPosition1 = new Vector3(Random.Range(600, 2000), 0,
            Random.Range(-500, 4000));
        GameObject typeToSpread;
        var random = Random.Range(0, 3);
        if (random < 1)
            typeToSpread = treeToSpread;
        else if (random < 2)
            typeToSpread = treeToSpread1;
        else
            typeToSpread = treeToSpread2;
        GameObject clone = Instantiate(typeToSpread, ranPosition, Quaternion.identity);
        GameObject clone1 = Instantiate(typeToSpread, ranPosition1, Quaternion.identity);
        clone.gameObject.transform.localScale += new Vector3(30, 30, 30);
        clone1.gameObject.transform.localScale += new Vector3(30, 30, 30);
        clone.gameObject.transform.rotation = Quaternion.Euler(0, Random.Range(0, 180), 0);
        clone1.gameObject.transform.rotation = Quaternion.Euler(0, Random.Range(0, 180), 0);
        trees.Add(clone.gameObject);
        trees.Add(clone1.gameObject);

    }
    
    void Update()
    {
        foreach (var tree in trees)
        {
            MoveTree(tree);
        }
    }

    private void MoveTree(GameObject o)
    {
        var dt = Time.deltaTime;
        var position = o.transform.localPosition;
        position.z += speed * dt;
        if (position.z <= -500)
            position.z = 4000;
        
        o.transform.localPosition = position;
    }
}

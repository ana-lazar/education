using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GenerateAndMoveTiles : MonoBehaviour
{
    // Start is called before the first frame update
    public int tileXPos = -500;
    public int nrOfTiles = 20;
    public Material material;
    public float speed = -100f;
    public List<GameObject> tiles = new List<GameObject>();
    void Start()
    {
        for (int i = 0; i < nrOfTiles; i++)
        {
            
            var brick = GameObject.CreatePrimitive(PrimitiveType.Cube);
            brick.AddComponent<Rigidbody>();
            brick.transform.localScale = new Vector3(10, 1, 30);
            brick.transform.position = new Vector3(0, 1, tileXPos);
            tileXPos += 150;
            tiles.Add(brick);
        }
        
    }

    // Update is called once per frame
    void Update()
    {
        foreach (var tile in tiles)
        {
            moveTree(tile);
        }
    }
    
    private void moveTree(GameObject o)
    {
        
        var dt = Time.deltaTime;
        var position = o.transform.localPosition;
        position.z += speed * dt;
        if (position.z <= -500)
            position.z = 4000;
        
        o.transform.localPosition = position;
    }
}

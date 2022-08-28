using UnityEngine;

public class BrickWall : MonoBehaviour
{
    public float brickLength, brickHeight, brickWidth;
    public float noOfCols, noOfRows;
    public Material material;
    
    void Start()
    {
        for (var i = 0; i < noOfCols; i++)
        {
            for (var j = 0; j < noOfRows; j++)
            {
                var position = transform.position;
                position.x += i * brickLength;
                position.y += j * brickHeight;
                
                var brick = GameObject.CreatePrimitive(PrimitiveType.Cube);
                brick.name = $"Brick{i}{j}";
                brick.AddComponent<Rigidbody>();
                brick.transform.localScale = new Vector3(brickLength, brickHeight, brickWidth);
                brick.transform.position = position;
                brick.transform.parent = transform;
                brick.GetComponent<Renderer>().material = material;
            }
        }
    }
}

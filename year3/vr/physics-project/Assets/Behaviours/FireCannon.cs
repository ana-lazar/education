using System.Collections;
using UnityEngine;

public class FireCannon : MonoBehaviour
{
    public GameObject cannonball;
    public float cannonballSpeed = 20;
    public Transform pof;
    public Transform barrel;
    public float scrollIncrements = 5;
    public float mouseSensitivity = 500.0f;

    void Update()
    {
        var rotateCannon = Input.GetAxis("Mouse X") * mouseSensitivity * Time.deltaTime;
        transform.Rotate(0, rotateCannon, 0);
        barrel.Rotate(Input.mouseScrollDelta.y * scrollIncrements, 0, 0);
        
        if (Input.GetButtonDown("Fire1"))
        {
            FireCannonball();
        }
    }
    
    void FireCannonball() {
        var ball = Instantiate(cannonball, pof.position, Quaternion.identity);
        var rb = ball.AddComponent<Rigidbody>();
        rb.velocity = cannonballSpeed * pof.forward;
        StartCoroutine(RemoveCannonball(ball));
    }
    
    IEnumerator RemoveCannonball(GameObject ball)
    {
        yield return new WaitForSeconds(2f);
        Destroy(ball);
    }
}

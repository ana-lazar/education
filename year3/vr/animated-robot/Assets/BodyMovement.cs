using UnityEngine;

public class BodyMovement : MonoBehaviour
{
    private float _v = -5.0f;
    
    void Update()
    {
        var dt = Time.deltaTime;
        var position = transform.localPosition;
        position.x += _v * dt;
        transform.localPosition = position;
    }
}

using Robot;
using UnityEngine;

public class RightHipMovement : MonoBehaviour
{
    private float MIN = -Constants.ANGLE;
    private float MAX = Constants.ANGLE;
    private float _v = Constants.VELOCITY;
    private float _x;
    
    void Update()
    {
        var dt = Time.deltaTime;
        _x += _v * dt;
        
        if (_x > MAX)
        {
            _v *= -1;
            _x = MAX;
        }

        if (_x < MIN)
        {
            _v *= -1;
            _x = MIN;
        }

        transform.localRotation = Quaternion.AngleAxis(_x, Vector3.forward);
    }
}

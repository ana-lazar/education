using Robot;
using UnityEngine;

public class LeftKneecapMovement : MonoBehaviour
{
    private float MIN = -Constants.ANGLE / 2;
    private float MAX = Constants.ANGLE / 2;
    private float _v = Constants.VELOCITY / 2;
    private float _x;
    private float _x1;

    void Update()
    {
        var dt = Time.deltaTime;
        _x1 += _v * dt;
        
        if (_x1 > MAX)
        {
            _v *= -1;
            _x1 = MAX;
        }

        if (_x1 < MIN)
        {
            _v *= -1;
            _x1 = MIN;
        }
        
        if (_x1 < 0)
        {
            _x = 0;
        }
        else
        {
            _x = _x1;
        }

        transform.localRotation = Quaternion.AngleAxis(_x, Vector3.forward);
    }
}

using UnityEngine;

public class CameraShakeScipt : MonoBehaviour
{
    void Update()
    {
        var speed = 10.0f; //how fast it shakes
        var amount = 0.1f; //how much it shakes
        var position = transform.localPosition;
        position.x = Mathf.Sin(Time.time * speed) * amount;
        transform.localPosition = position;
    }
}

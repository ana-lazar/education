using UnityEngine;

public class PlayerMovement : MonoBehaviour
{
    public CharacterController controller;
    public float speed = 12.0f;

    void Update()
    {
        var x = Input.GetAxis("Horizontal");
        var z = Input.GetAxis("Vertical");

        var t = transform;
        var move = t.right * x + t.forward * z;

        controller.Move(move * speed * Time.deltaTime);
    }
}

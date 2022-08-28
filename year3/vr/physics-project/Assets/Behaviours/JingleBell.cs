using UnityEngine;

public class JingleBell : MonoBehaviour
{
    public AudioSource jingleBell;

    private void OnCollisionEnter(Collision other)
    {
        jingleBell.Play();
    }
}

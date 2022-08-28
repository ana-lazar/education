using UnityEngine;

public class HeadToCamera : MonoBehaviour
{
    void Update()
    {
        Ray mouseRay = Camera.main.ScreenPointToRay(Input.mousePosition);
        float midPoint = (transform.position - Camera.main.transform.position).magnitude * 0.5f;
        
        transform.LookAt(mouseRay.origin + mouseRay.direction * midPoint);
        transform.Rotate(Vector3.up, 90);
    }
}

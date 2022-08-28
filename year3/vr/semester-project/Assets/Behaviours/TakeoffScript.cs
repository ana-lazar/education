using System.Collections;
using UnityEngine;

public class TakeoffScript : MonoBehaviour
{
    public float speed = 100f;
    public float posMax = 167f;
    public float posMin = 68f;

    IEnumerator Waiter()
    {
        yield return new WaitForSeconds(2f);
        speed = -100;
    }
    
    IEnumerator Waiter2()
    {
        yield return new WaitForSeconds(2f);
        speed = 100;
    }
    
    void Update()
    {
        var position = transform.localPosition;
        
        var dt = Time.deltaTime;
        position.y += speed * dt;
        
        if (position.y >= posMax)
        {
            position.y = posMax-1;
            StartCoroutine(Waiter());
        }
        
        if (position.y <= posMin)
        {
            position.y = posMin+1;
            StartCoroutine(Waiter2());
        }
        
        transform.localPosition = position;
    }
}
using System.Collections;
using UnityEngine;

public class CameraShake : MonoBehaviour
{
    public float interval = 5.0f;
    public float duration = 0.15f;
    public float magnitude = 0.4f;
    public bool isRandom;

    void Start()
    {
        InvokeRepeating(nameof(AsyncShake), interval, interval);
    }

    private void AsyncShake()
    {
        StartCoroutine(Shake());
    }

    private IEnumerator Shake()
    {
        var originalPos = transform.localPosition;
        var elapsed = 0.0f;

        if (isRandom)
        {
            duration = Random.Range(1, 7);
            magnitude = Random.Range(0.1f, 0.3f);
        }

        while (elapsed < duration)
        {
            var x = Random.Range(-1f, 1f) * magnitude;
            var y = Random.Range(-1f, 1f) * magnitude;

            transform.localPosition = new Vector3(x, y, originalPos.z);

            elapsed += Time.deltaTime;

            // inainte sa se treaca la urmatoare linie se asteapta reapelarea functiei Update
            yield return null;
        }

        transform.localPosition = originalPos;
    }
}

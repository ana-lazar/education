using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlaySound : MonoBehaviour
{
    public AudioSource sound;
    private bool _isPlaying;
    
    void Start()
    {
        sound.Play();
    }

    void Update()
    {
        if (Input.GetMouseButtonDown(1))
        {
            _isPlaying = !_isPlaying;
            
            if (_isPlaying)
                sound.Play();
            else
                sound.Stop();
        }
    }
}

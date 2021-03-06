﻿using System;

using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Domain.Dtos
{
    public class ParticipantInfo
    {
        public Race Race { get; set; }
        public Participant Participant { get; set; }

        public ParticipantInfo(Race race, Participant participant)
        {
            this.Race = race;
            this.Participant = participant;
        }

        public override string ToString()
        {
            return "Name: " + Participant.Name + ", Race: " + Race.Capacity;
        }
    }
}

using MotorcycleContest.Domain.Entities;
using MotorcycleContest.Services;
using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using System.Windows.Forms;
using MotorcycleContest.Domain.Dtos;

namespace motorcycle_contest
{
    public partial class MainForm : Form
    {
        private MotorcycleContestService service;
        private User user;

        public MainForm(MotorcycleContestService service, User user)
        {
            this.service = service;
            this.user = user;
            InitializeComponent();
            InitCheckedList();
            for (int i = 0; i < capacityCheckedListBox.Items.Count; i++)
            {
                capacityCheckedListBox.SetItemChecked(i, true);
            }
            InitRacesList();
            welcomeLabel.Text = "WELCOME, " + user.Name;
        }

        private void logoutButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void registerButton_Click(object sender, EventArgs e)
        {
            RegisterForm registerForm = new RegisterForm(this.service);
            registerForm.Show();
        }

        private void InitCheckedList()
        {
            try
            {
                capacityCheckedListBox.ClearSelected();
                foreach (Race race in service.GetRaces())
                {
                    capacityCheckedListBox.Items.Add(race.Capacity.ToString());
                }
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        private void InitRacesList()
        {
            try
            {
                racesListView.Clear();
                foreach (String capacity in capacityCheckedListBox.CheckedItems)
                {
                    racesListView.Items.Add(service.GetRaceByCapacity(Int32.Parse(capacity)).ToString());
                }
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }
        
        private void InitParticipantsList()
        {
            try
            {
                participantsListView.Clear();
                List<ParticipantInfo> participants = service.GetRaceParticipantsByTeam(teamTextBox.Text);
                foreach (ParticipantInfo race in participants)
                {
                    participantsListView.Items.Add(race.ToString());
                }
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        private void searchButton_Click(object sender, EventArgs e)
        {
            if (teamTextBox.Text == "")
            {
                MessageBox.Show("Make sure all the fields are filled.");
                return;
            }
            InitParticipantsList();
        }

        private void racesListView_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void capacityCheckedListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            InitRacesList();
        }

        private void capacityCheckedListBox_ItemChecked(object sender, EventArgs e)
        {
            InitRacesList();
        }
    }
}

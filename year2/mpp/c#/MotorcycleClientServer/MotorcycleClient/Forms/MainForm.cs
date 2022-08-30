using System;
using System.Collections.Generic;
using System.Windows.Forms;
using motorcycle_contest.Controllers;
using MotorcycleContest.Domain.Entities;
using MotorcycleContest.Domain.Dtos;

namespace motorcycle_contest
{
    public partial class MainForm : Form
    {
        private MotorcycleController controller;
        private LoginForm loginForm;
        private User user;

        public MainForm(MotorcycleController controller, User user)
        {
            this.controller = controller;
            this.user = user;
            InitializeComponent();
            Load += MainForm_Load;
        }

        private void RacesUpdate(object sender, MotorcycleEventArgs e)
        {
            if (e.MotorcycleEvent == MotorcycleEvent.REGISTERED_PARTICIPANT)
            {
                this.BeginInvoke(new UpdateCallBack(this.InitRacesList));
                this.BeginInvoke(new UpdateCallBack(this.InitParticipantsList));
            }
        }

        public delegate void UpdateCallBack();

        private void logoutButton_Click(object sender, EventArgs e)
        {
            this.Close();
            loginForm.Show();
        }

        private void registerButton_Click(object sender, EventArgs e)
        {
            RegisterForm registerForm = new RegisterForm(this.controller);
            registerForm.Show();
        }

        private void InitCheckedList()
        {
            try
            {
                capacityCheckedListBox.Items.Clear();
                capacityCheckedListBox.ClearSelected();
                foreach (Race race in controller.GetRaces())
                {
                    capacityCheckedListBox.Items.Add(race.Capacity.ToString());
                }
                for (int i = 0; i < capacityCheckedListBox.Items.Count; i++)
                {
                    capacityCheckedListBox.SetItemChecked(i, true);
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
                racesListView.Items.Clear();
                foreach (String capacity in capacityCheckedListBox.CheckedItems)
                {
                    racesListView.Items.Add(controller.GetRaceByCapacity(Int32.Parse(capacity)).ToString());
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
                List<ParticipantDto> participants = controller.GetRaceParticipantsByTeam(teamTextBox.Text);
                foreach (ParticipantDto race in participants)
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

        private void capacityCheckedListBox_ItemChecked(object sender, ItemCheckEventArgs e)
        {
            InitRacesList();
        }

        private void panel1_Paint(object sender, PaintEventArgs e)
        {
            
        }

        public void setLoginForm(LoginForm loginForm)
        {
            this.loginForm = loginForm;
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            MotorcycleController.UpdateEvent += RacesUpdate;
            FormClosed += MainForm_FormClosed;
            InitCheckedList();
            InitRacesList();
            welcomeLabel.Text = "WELCOME, " + user.Name;
            capacityCheckedListBox.ItemCheck += capacityCheckedListBox_ItemChecked;
        }
        
        private void MainForm_FormClosed(object sender, EventArgs e)
        {
            MotorcycleController.UpdateEvent -= RacesUpdate;
        }
    }
}

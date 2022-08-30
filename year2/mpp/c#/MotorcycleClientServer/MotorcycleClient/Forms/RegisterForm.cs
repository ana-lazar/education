using System;
using System.Windows.Forms;
using motorcycle_contest.Controllers;
using MotorcycleContest.Domain.Entities;

namespace motorcycle_contest
{
    public partial class RegisterForm : Form
    {
        private readonly MotorcycleController controller;
        
        public RegisterForm(MotorcycleController controller)
        {
            this.controller = controller;
            InitializeComponent();
            InitComboBox();
            capacityComboBox.SelectedIndex = 0;
        }

        private void InitComboBox()
        {
            try
            {
                foreach (Race race in controller.GetRaces())
                {
                    capacityComboBox.Items.Add(race.Capacity);
                }
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
            
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void saveButton_Click(object sender, EventArgs e)
        {
            if (nameTextBox.Text == "")
            {
                MessageBox.Show("Make sure all the fields are filled.");
                return;
            }
            try
            {
                Int32 capacity = Int32.Parse(capacityComboBox.SelectedItem.ToString());
                controller.RegisterParticipant(nameTextBox.Text, teamTextBox.Text, capacity);
                MessageBox.Show("Participant successfully added.");
                nameTextBox.Clear();
                teamTextBox.Clear();
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }
    }
}
using System;
using System.Windows.Forms;
using MotorcycleContest.Domain.Entities;
using MotorcycleContest.Services;

namespace motorcycle_contest
{
    public partial class RegisterForm : Form
    {
        private readonly MotorcycleContestService _service;
        
        public RegisterForm(MotorcycleContestService service)
        {
            this._service = service;
            InitializeComponent();
            InitComboBox();
            capacityComboBox.SelectedIndex = 0;
        }

        private void InitComboBox()
        {
            try
            {
                foreach (Race race in _service.GetRaces())
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
                _service.RegisterParticipant(nameTextBox.Text, teamTextBox.Text, capacity);
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
using MotorcycleContest.Domain.Entities;
using MotorcycleContest.Services;
using System;
using System.Windows.Forms;

namespace motorcycle_contest
{
    public partial class LoginForm : Form
    {
        private MotorcycleContestService service;

        public LoginForm(MotorcycleContestService service)
        {
            this.service = service;
            InitializeComponent();
        }

        private void button1_Click(object sender, System.EventArgs e)
        {
            if (usernameTextBox.Text == "" || passwordTextBox.Text == "")
            {
                MessageBox.Show("Please complete all the fields");
                return;
            }
            try { 
                User user = this.service.Authenticate(usernameTextBox.Text, passwordTextBox.Text);
                if (user == null)
                {
                    usernameTextBox.Clear();
                    passwordTextBox.Clear();
                    MessageBox.Show("Username or password incorrect");
                }
                else {
                    usernameTextBox.Clear();
                    passwordTextBox.Clear();
                    MainForm mainForm = new MainForm(this.service, user);
                    mainForm.Show();
                }
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }
    }
}

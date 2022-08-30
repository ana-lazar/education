using System;
using System.Web.UI.HtmlControls;
using System.Windows.Forms;
using motorcycle_contest.Controllers;
using MotorcycleContest.Domain.Entities;

namespace motorcycle_contest
{
    public partial class LoginForm : Form
    {
        private MotorcycleController controller;

        public LoginForm(MotorcycleController controller)
        {
            this.controller = controller;
            InitializeComponent();
        }

        private void button1_Click(object sender, System.EventArgs e)
        {
            if (usernameTextBox.Text == "" || passwordTextBox.Text == "")
            {
                MessageBox.Show("Please enter all the fields");
                return;
            }
            try { 
                User user = controller.Authenticate(usernameTextBox.Text, passwordTextBox.Text);
                if (user == null)
                {
                    usernameTextBox.Clear();
                    passwordTextBox.Clear();
                    MessageBox.Show("Username or password incorrect");
                }
                else {
                    usernameTextBox.Clear();
                    passwordTextBox.Clear();
                    MainForm mainForm = new MainForm(this.controller, user);
                    mainForm.Show();
                    mainForm.setLoginForm(this);
                    Hide();
                }
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }
    }
}

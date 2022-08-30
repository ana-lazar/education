namespace motorcycle_contest
{
    partial class MainForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.welcomeLabel = new System.Windows.Forms.Label();
            this.registerButton = new System.Windows.Forms.Button();
            this.racesListView = new System.Windows.Forms.ListView();
            this.racesLabel = new System.Windows.Forms.Label();
            this.logoutButton = new System.Windows.Forms.Button();
            this.participantsLabel = new System.Windows.Forms.Label();
            this.participantsListView = new System.Windows.Forms.ListView();
            this.teamTextBox = new System.Windows.Forms.TextBox();
            this.searchButton = new System.Windows.Forms.Button();
            this.panel1 = new System.Windows.Forms.Panel();
            this.capacityCheckedListBox = new System.Windows.Forms.CheckedListBox();
            this.label1 = new System.Windows.Forms.Label();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // welcomeLabel
            // 
            this.welcomeLabel.AutoSize = true;
            this.welcomeLabel.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.welcomeLabel.Font = new System.Drawing.Font("HelvLight", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.welcomeLabel.Location = new System.Drawing.Point(145, 10);
            this.welcomeLabel.Name = "welcomeLabel";
            this.welcomeLabel.Size = new System.Drawing.Size(165, 35);
            this.welcomeLabel.TabIndex = 1;
            this.welcomeLabel.Text = "WELCOME";
            // 
            // registerButton
            // 
            this.registerButton.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.registerButton.Font = new System.Drawing.Font("HelvLight", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.registerButton.Location = new System.Drawing.Point(520, 9);
            this.registerButton.Name = "registerButton";
            this.registerButton.Size = new System.Drawing.Size(97, 36);
            this.registerButton.TabIndex = 2;
            this.registerButton.Text = "Register";
            this.registerButton.UseVisualStyleBackColor = false;
            this.registerButton.Click += new System.EventHandler(this.registerButton_Click);
            // 
            // racesListView
            // 
            this.racesListView.Alignment = System.Windows.Forms.ListViewAlignment.Default;
            this.racesListView.HideSelection = false;
            this.racesListView.Location = new System.Drawing.Point(25, 190);
            this.racesListView.Name = "racesListView";
            this.racesListView.Size = new System.Drawing.Size(256, 169);
            this.racesListView.TabIndex = 3;
            this.racesListView.UseCompatibleStateImageBehavior = false;
            this.racesListView.View = System.Windows.Forms.View.List;
            this.racesListView.SelectedIndexChanged += new System.EventHandler(this.racesListView_SelectedIndexChanged);
            // 
            // racesLabel
            // 
            this.racesLabel.AutoSize = true;
            this.racesLabel.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.racesLabel.Font = new System.Drawing.Font("HelvLight", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.racesLabel.Location = new System.Drawing.Point(116, 69);
            this.racesLabel.Name = "racesLabel";
            this.racesLabel.Size = new System.Drawing.Size(74, 24);
            this.racesLabel.TabIndex = 5;
            this.racesLabel.Text = "RACES";
            // 
            // logoutButton
            // 
            this.logoutButton.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.logoutButton.Font = new System.Drawing.Font("HelvLight", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.logoutButton.Location = new System.Drawing.Point(12, 9);
            this.logoutButton.Name = "logoutButton";
            this.logoutButton.Size = new System.Drawing.Size(97, 36);
            this.logoutButton.TabIndex = 6;
            this.logoutButton.Text = "Log Out";
            this.logoutButton.UseVisualStyleBackColor = false;
            this.logoutButton.Click += new System.EventHandler(this.logoutButton_Click);
            // 
            // participantsLabel
            // 
            this.participantsLabel.AutoSize = true;
            this.participantsLabel.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.participantsLabel.Font = new System.Drawing.Font("HelvLight", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.participantsLabel.Location = new System.Drawing.Point(398, 69);
            this.participantsLabel.Name = "participantsLabel";
            this.participantsLabel.Size = new System.Drawing.Size(147, 24);
            this.participantsLabel.TabIndex = 7;
            this.participantsLabel.Text = "PARTICIPANTS";
            // 
            // participantsListView
            // 
            this.participantsListView.HideSelection = false;
            this.participantsListView.Location = new System.Drawing.Point(348, 161);
            this.participantsListView.Name = "participantsListView";
            this.participantsListView.Size = new System.Drawing.Size(256, 199);
            this.participantsListView.TabIndex = 8;
            this.participantsListView.UseCompatibleStateImageBehavior = false;
            this.participantsListView.View = System.Windows.Forms.View.List;
            // 
            // teamTextBox
            // 
            this.teamTextBox.Font = new System.Drawing.Font("HelvLight", 7.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.teamTextBox.Location = new System.Drawing.Point(348, 120);
            this.teamTextBox.Name = "teamTextBox";
            this.teamTextBox.Size = new System.Drawing.Size(156, 23);
            this.teamTextBox.TabIndex = 9;
            // 
            // searchButton
            // 
            this.searchButton.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.searchButton.Font = new System.Drawing.Font("HelvLight", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.searchButton.Location = new System.Drawing.Point(520, 115);
            this.searchButton.Name = "searchButton";
            this.searchButton.Size = new System.Drawing.Size(84, 34);
            this.searchButton.TabIndex = 10;
            this.searchButton.Text = "Search";
            this.searchButton.UseVisualStyleBackColor = false;
            this.searchButton.Click += new System.EventHandler(this.searchButton_Click);
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ControlLightLight;
            this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.panel1.Controls.Add(this.capacityCheckedListBox);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.registerButton);
            this.panel1.Controls.Add(this.logoutButton);
            this.panel1.Controls.Add(this.welcomeLabel);
            this.panel1.Location = new System.Drawing.Point(12, 12);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(631, 373);
            this.panel1.TabIndex = 104;
            this.panel1.Paint += new System.Windows.Forms.PaintEventHandler(this.panel1_Paint);
            // 
            // capacityCheckedListBox
            // 
            this.capacityCheckedListBox.FormattingEnabled = true;
            this.capacityCheckedListBox.Location = new System.Drawing.Point(98, 107);
            this.capacityCheckedListBox.Name = "capacityCheckedListBox";
            this.capacityCheckedListBox.Size = new System.Drawing.Size(170, 64);
            this.capacityCheckedListBox.TabIndex = 8;
            this.capacityCheckedListBox.SelectedIndexChanged += new System.EventHandler(this.capacityCheckedListBox_SelectedIndexChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(21, 110);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(71, 18);
            this.label1.TabIndex = 7;
            this.label1.Text = "Capacity:";
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 18F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(655, 397);
            this.Controls.Add(this.searchButton);
            this.Controls.Add(this.teamTextBox);
            this.Controls.Add(this.participantsListView);
            this.Controls.Add(this.participantsLabel);
            this.Controls.Add(this.racesLabel);
            this.Controls.Add(this.racesListView);
            this.Controls.Add(this.panel1);
            this.Font = new System.Drawing.Font("HelvLight", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.Name = "MainForm";
            this.Text = "MainForm";
            this.Load += new System.EventHandler(this.MainForm_Load);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        #endregion
        private System.Windows.Forms.Label welcomeLabel;
        private System.Windows.Forms.Button registerButton;
        private System.Windows.Forms.ListView racesListView;
        private System.Windows.Forms.Label racesLabel;
        private System.Windows.Forms.Button logoutButton;
        private System.Windows.Forms.Label participantsLabel;
        private System.Windows.Forms.ListView participantsListView;
        private System.Windows.Forms.TextBox teamTextBox;
        private System.Windows.Forms.Button searchButton;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.CheckedListBox capacityCheckedListBox;
        private System.Windows.Forms.Label label1;
    }
}
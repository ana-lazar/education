describe("Demoblaze Invalid Login ", () => {
  it("Invalid login test case", () => {
    cy.visit("https://www.demoblaze.com/");
    //invalid login
    cy.get("input#loginusername.form-control").type(
      Cypress.env("demoblazeUser"),
      { force: true }
    );
    cy.get("input#loginpassword.form-control").type("Demoblaze", {
      force: true,
    });
    cy.get("button.btn.btn-primary").eq(2).click({ force: true });
    cy.on("window:alert", (text) => {
      expect(text).to.contains("Wrong password");
    });
  });
});

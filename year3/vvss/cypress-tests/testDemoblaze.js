describe("Demoblaze Login", () => {
  it("Login test case", () => {
    cy.visit("https://www.demoblaze.com/");

    //login
    cy.get("input#loginusername.form-control").type(
      Cypress.env("demoblazeUser"),
      { force: true }
    );
    cy.get("input#loginpassword.form-control").type(
      Cypress.env("demoblazePass"),
      { force: true }
    );
    cy.get("button.btn.btn-primary").eq(2).click({ force: true });
    cy.wait(500);
    cy.get("div#navbarExample.navbar-collapse").should(($nav) => {
      expect($nav.last()).to.contain("Welcome adipavi");
    });
    //logout
    cy.get("a#logout2.nav-link").click();
    cy.get("div#navbarExample.navbar-collapse").should(($nav) => {
      expect($nav.last()).to.contain("Sign up");
    });
  });
});

describe("Demoblaze Add Product", () => {
  it("Add Product test case", () => {
    cy.visit("https://www.demoblaze.com/");
    cy.get("input#loginusername.form-control").type(
      Cypress.env("demoblazeUser"),
      { force: true }
    );
    cy.get("input#loginpassword.form-control").type(
      Cypress.env("demoblazePass"),
      { force: true }
    );
    cy.get("button.btn.btn-primary").eq(2).click({ force: true });
    cy.wait(500);
    // adaugare in cos
    cy.get("a.hrefch").eq(0).click();
    cy.get("a.btn.btn-success.btn-lg").eq(0).click();
    cy.on("window:alert", (text) => {
      expect(text).to.contains("Product added");
    });
    cy.wait(500);
    //logout
    cy.get("a#logout2.nav-link").click();
    cy.get("div#navbarExample.navbar-collapse").should(($nav) => {
      expect($nav.last()).to.contain("Sign up");
    });
  });
});

describe("Demoblaze Delete Product", () => {
  it("Delete Product test case", () => {
    cy.visit("https://www.demoblaze.com/");

    //login
    cy.get("input#loginusername.form-control").type(
      Cypress.env("demoblazeUser"),
      { force: true }
    );
    cy.get("input#loginpassword.form-control").type(
      Cypress.env("demoblazePass"),
      { force: true }
    );
    cy.get("button.btn.btn-primary").eq(2).click({ force: true });
    cy.wait(500);
    cy.get("div#navbarExample.navbar-collapse").should(($nav) => {
      expect($nav.last()).to.contain("Welcome adipavi");
    });
    // adaugare in cos
    cy.get("a.hrefch").eq(0).click();
    cy.get("a.btn.btn-success.btn-lg").eq(0).click();
    cy.on("window:alert", (text) => {
      expect(text).to.contains("Product added");
    });
    cy.wait(500);
    // stergere din cos
    cy.get("a#cartur.nav-link").click();
    cy.get("tr.success").eq(0).find("a").click({ force: true });
    cy.wait(500);
    cy.get("h3#totalp").should(($text) => {
      expect($text).to.be.empty;
    });
    cy.wait(500);
    //logout
    cy.get("a#logout2.nav-link").click();
    cy.get("div#navbarExample.navbar-collapse").should(($nav) => {
      expect($nav.last()).to.contain("Sign up");
    });
  });
});

describe("Demoblaze Logout", () => {
  it("Logout test case", () => {
    cy.visit("https://www.demoblaze.com/");

    //login
    cy.get("input#loginusername.form-control").type(
      Cypress.env("demoblazeUser"),
      { force: true }
    );
    cy.get("input#loginpassword.form-control").type(
      Cypress.env("demoblazePass"),
      { force: true }
    );
    cy.get("button.btn.btn-primary").eq(2).click({ force: true });
    cy.wait(500);
    cy.get("div#navbarExample.navbar-collapse").should(($nav) => {
      expect($nav.last()).to.contain("Welcome adipavi");
    });
    //logout
    cy.get("a#logout2.nav-link").click();
    cy.get("div#navbarExample.navbar-collapse").should(($nav) => {
      expect($nav.last()).to.contain("Sign up");
    });
  });
});

import { AnsibleWebFrontPage } from './app.po';

describe('ansible-web-front App', () => {
  let page: AnsibleWebFrontPage;

  beforeEach(() => {
    page = new AnsibleWebFrontPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});

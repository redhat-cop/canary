import { AnsibleLvm } from './ansible-lvm';

describe('AnsibleLvm', () => {
  it('should create an instance', () => {
    expect(new AnsibleLvm()).toBeTruthy();
  });
});

package com.taskagile.domain.model.user;

import com.taskagile.domain.common.security.PasswordEncryptor;

/**
 * User registration domain service
 */
public class RegistrationManagement {

  private UserRepository repository;
  private PasswordEncryptor passwordEncryptor;

  public RegistrationManagement(UserRepository a, PasswordEncryptor b) {
    this.repository = a;
    this.passwordEncryptor = b;
  }

  public User register(String username, String emailAddress, String password)
    throws RegistrationException {
    User existingUser = repository.findByUsername(username);
    if (existingUser != null) {
      throw new UsernameExistsException();
    }

    existingUser = repository.findByEmailAddress(emailAddress.toLowerCase());
    if (existingUser != null) {
      throw new EmailAddressExistsException();
    }

    String encryptedPassword = passwordEncryptor.encrypt(password);
    User newUser = User.create(username, emailAddress.toLowerCase(), encryptedPassword);
    repository.save(newUser);
    return newUser;
  }
}

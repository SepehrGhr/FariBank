# 🏦 FariBank

<div align="center">

![FariBank Logo](https://via.placeholder.com/300?text=FariBank)

**A comprehensive banking system with multiple user roles, extensive account management, and financial services.**

[![Java](https://img.shields.io/badge/Java-8%2B-orange.svg)](https://www.java.com)
[![License](https://img.shields.io/badge/License-Proprietary-blue.svg)](LICENSE)

</div>

## 📋 Contents

- [Features](#-features)
- [User Options](#-user-options)
- [Getting Started](#-getting-started)
- [System Requirements](#-system-requirements)
- [Development Information](#-development-information)

## ✨ Features

### 👥 User Roles
- **Customer** - Manage accounts, transfer funds, and access various banking services
- **Admin** - Handle user authentication requests and manage support tickets
- **Manager** - Configure system settings and manage users and staff

### 💰 Account Management
- View account balance and details
- Check credit card information
- Review transaction history
- Generate account reports in HTML format with charts

### 💸 Money Management
- Transfer funds between accounts (via Account ID)
- Send money to contacts
- Set up recurring payments via Paya system
- View and filter receipt history

### 📊 Fund Types
- **Saving Fund** - Basic savings account
- **Interest Fund** - Earn interest on deposits (configurable rates)
- **Remainder Fund** - Automatically collect spare change from transactions

### 📱 Contact Management
- Add and edit contacts
- Activate/deactivate contact functionality
- Send money directly to contacts
- View contact information

### 📞 Mobile Services
- Check SIM card balance
- Recharge your own SIM card
- Add credit to contacts' or other phone numbers

### 🔒 Security Features
- Secure authentication system
- Password protection for accounts and credit cards
- User verification by admin
- Account blocking/unblocking by managers

### 🎫 Support System
- Create support tickets
- View ticket status and responses
- Admin ticket management

### ⚙️ Administration
- User registration approval
- Fee rate management
- Interest rate configuration
- System-wide settings control

## 🔍 User Options

<table>
  <tr>
    <th>Customer Main Menu</th>
    <th>Admin Menu</th>
    <th>Manager Menu</th>
  </tr>
  <tr>
    <td>
      1. Account Management<br>
      2. Funds Management<br>
      3. Contacts<br>
      4. Transfer Money<br>
      5. Charge SIM<br>
      6. Support<br>
      7. Settings<br>
      8. Account Details<br>
      9. Log Out<br>
      10. Quit
    </td>
    <td>
      1. Tickets<br>
      2. Users<br>
      3. Log Out
    </td>
    <td>
      1. Settings<br>
      2. User Management<br>
      3. Auto Transaction<br>
      4. Log Out
    </td>
  </tr>
</table>

## 🚀 Getting Started

1. Run the application
2. Select your role (User, Admin, or Manager)
3. For new users, select "Sign Up" to create an account
4. For existing users, select "Login" and provide your credentials
5. Navigate through the menu options to access various features


## 🛠️ Development Information

Built using Java with object-oriented design principles. The application follows a clean architecture with separate classes for different functionalities:
- User management
- Authentication
- Fund management
- Receipt processing
- Transaction handling

---


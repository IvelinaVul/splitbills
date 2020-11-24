# Splitbills
##  Splitbills is a university project developed for the Modern Java Technologies course. It’s aim is to provide users with a way to monitor their shared expences with different groups of accquiantaces more easily. 

##  Splitbills let’s users create and give names to groups, so that they could  keep track of the reasons for sharing the bills. Every member of a group can split expenses with the other group members and provide a reason for paying. Thus, a user can not only monitor the money they owe, but also look at the amounts other people owe to him and the reasons behind. 

### Project Requirements:

####  Functional Requirements:
- Users should be able to login and register with a password and unique username.
- A user should be able to create a group with other registered accquiantaces. Groups must be given a name and must consist of two or more users.
- Users can split expenses with a certain group. The sum to be splitted is divided equally among all the group members. If there are previous unsettled debts, their amount should be deducted. Splitting expenses is in the form of creating debts. No real money is transmitted.
- Users should be able to get how much money they owe and they lended.
- Users should be able to pay for expenses. Payments are only in the form of clearing debts. No real money is transmitted.

####  Nonfunctional Requirements
- The client should be a console application.
- The server should be able to serve multiple clients .
- If an error occurrs in the client, it should be saved in a file. No concrete format is required.
- If an error occurs in the server, it should be displayed in the console and saved in a file. No concrete format is required.
- The server side should be implemented using java.net or java.nio. 


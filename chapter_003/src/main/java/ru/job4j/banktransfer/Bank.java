package ru.job4j.banktransfer;

import java.util.*;

/**
 * @author Kosolapov Ilya(d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Bank {
    private Map<User, List<Account>> clients = new HashMap<>();

    private User getUser(String passport) {
        User result = null;
        Set<User> users = clients.keySet();
        for (User user : users) {
            if (user.getPassport().equals(passport)) {
                result = user;
                break;
            }
        }
        return result;
    }

    /**
     * Add new user
     *
     * @param user user
     */
    public void addUser(User user) {
        clients.putIfAbsent(user, new ArrayList<>());
    }

    /**
     * Delete user
     *
     * @param user user
     */
    public void delete(User user) {
        clients.remove(user);
    }

    /**
     * Add account for user
     *
     * @param passport user's passport data
     * @param account  new account data
     */
    public void addAccountToUser(String passport, Account account) {
        clients.get(getUser(passport)).add(account);
    }

    /**
     * Delete user's account
     *
     * @param passport user's passport data
     * @param account  new account data
     */
    public void deleteAccountFromUser(String passport, Account account) {

        List<Account> accounts = clients.get(getUser(passport));

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getRequisites().equals(account.getRequisites())) {
                accounts.remove(i);
                break;
            }
        }
    }

    /**
     * Get user's accounts
     *
     * @param passport user's passport data
     * @return list accounts
     */
    public List<Account> getUserAccounts(String passport) {
        return clients.get(getUser(passport));
    }

    /**
     * Transfer money between users account
     *
     * @param srcPassport  passport source
     * @param srcRequisite requisites source
     * @param destPassport passport destination
     * @param dstRequisite passport destination
     * @param amount       money volume
     * @return result true if success
     */
    public boolean transferMoney(String srcPassport,
                                 String srcRequisite,
                                 String destPassport,
                                 String dstRequisite, double amount) {

        boolean transfer = false;
        for (Account srcAccount : getUserAccounts(srcPassport)) {
            if (srcAccount.getRequisites().equals(srcRequisite) && srcAccount.getValue() >= amount) {
                for (Account destAccount : getUserAccounts(destPassport)) {
                    if (destAccount.getRequisites().equals(dstRequisite)) {
                        srcAccount.debitValue(amount);
                        destAccount.addValue(amount);
                        transfer = true;
                    }
                }

            }

        }


        return transfer;

    }

}

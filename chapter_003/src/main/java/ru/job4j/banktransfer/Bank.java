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

    private Account getAccount(String passport, String requisites) {
        List<Account> accounts = getUserAccounts(passport);
        Account account = null;
        for (Account a : accounts) {
            if (a.getRequisites().equals(requisites)) {
                account = a;
                break;
            }
        }
        return account;
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
        User user = getUser(passport);
        if (!Objects.isNull(user)) {
            clients.get(user).add(account);
        }
    }

    /**
     * Delete user's account
     *
     * @param passport user's passport data
     * @param account  new account data
     */
    public void deleteAccountFromUser(String passport, Account account) {
        User user = getUser(passport);
        if (!Objects.isNull(user)) {
            List<Account> accounts = clients.get(user);
            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).getRequisites().equals(account.getRequisites())) {
                    accounts.remove(i);
                    break;
                }
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
        User user = getUser(passport);
        List<Account> accounts = null;
        if (!Objects.isNull(user)) {
            accounts = clients.get(user);
        }
        return accounts;
    }

    /**
     * Transfer money between users account
     *
     * @param srcPassport  passport source
     * @param srcRequisite requisites source
     * @param destPassport passport destination
     * @param dstRequisite requisites destination
     * @param amount       money volume
     * @return result true if success
     */
    public boolean transferMoney(String srcPassport,
                                 String srcRequisite,
                                 String destPassport,
                                 String dstRequisite, double amount) {

        boolean transfer = false;

        Account srcAccount = getAccount(srcPassport, srcRequisite);
        Account dstAccount = getAccount(destPassport, dstRequisite);

        if (!Objects.isNull(srcAccount) && !Objects.isNull(dstAccount)) {
            if (srcAccount.getValue() >= amount) {
                srcAccount.debitValue(amount);
                dstAccount.addValue(amount);
                transfer = true;
            }
        }

        /*
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
        */

        return transfer;

    }

}

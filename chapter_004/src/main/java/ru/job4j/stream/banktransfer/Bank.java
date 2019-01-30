package ru.job4j.stream.banktransfer;

import java.util.*;

/**
 * @author Kosolapov Ilya(d_dexter@mail.ru)
 * @version $ID$
 * @since 0.1
 */
public class Bank {
    private Map<User, List<Account>> clients = new HashMap<>();

    private User getUser(String passport) {
        return clients.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .filter(x->x.getPassport().equals(passport))
                .findFirst().orElse(null);
    }

    private Account getAccount(String passport, String requisites) {
        List<Account> accounts = getUserAccounts(passport);
        Account account = null;
        if (!Objects.isNull(accounts)) {
            account = accounts
                    .stream()
                    .filter(x -> x.getRequisites().equals(requisites))
                    .findFirst()
                    .orElse(null);
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

        List<Account> accounts = getUserAccounts(passport);
        if (!Objects.isNull(accounts)) {
            accounts.removeIf(x->x.getRequisites()
                    .equals(account.getRequisites()));
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

        return transfer;

    }

}

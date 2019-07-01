//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.neo4j.jdbc.bolt.impl;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neo4j.driver.v1.AccessMode;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.jdbc.Neo4jDatabaseMetaData;
import org.neo4j.jdbc.bolt.BoltNeo4jConnection;
import org.neo4j.jdbc.bolt.BoltNeo4jDatabaseMetaData;
import org.neo4j.jdbc.bolt.BoltNeo4jPreparedStatement;
import org.neo4j.jdbc.bolt.BoltNeo4jStatement;
import org.neo4j.jdbc.impl.Neo4jConnectionImpl;
import org.neo4j.jdbc.utils.Neo4jInvocationHandler;
import org.neo4j.jdbc.utils.TimeLimitedCodeBlock;

public class BoltNeo4jConnectionImpl extends Neo4jConnectionImpl implements BoltNeo4jConnection {

    private Driver driver;

    private Session session;

    private Transaction transaction;

    private boolean autoCommit = true;

    private BoltNeo4jDatabaseMetaData metadata;

    private static final Logger LOGGER = Logger.getLogger(BoltNeo4jConnectionImpl.class.getName());

    public BoltNeo4jConnectionImpl(Driver driver, Properties properties, String url) {
        super(properties, url, 2);
        this.driver = driver;
        this.initSession();
    }

    public static BoltNeo4jConnection newInstance(Driver driver, Properties info, String url) {
        BoltNeo4jConnection boltConnection = new BoltNeo4jConnectionImpl(driver, info, url);
        return (BoltNeo4jConnection) Proxy.newProxyInstance(BoltNeo4jConnectionImpl.class.getClassLoader(),
                                                            new Class[]{Connection.class, BoltNeo4jConnection.class},
                                                            new Neo4jInvocationHandler(boltConnection, hasDebug(info)));
    }

    @Override
    public Transaction getTransaction() {
        this.initTransaction();
        return this.transaction;
    }

    @Override
    public Session getSession() {
        return this.session;
    }

    @Override
    public Neo4jDatabaseMetaData getMetaData() {
        // 用于解决 neo4j-jdbc 的 bug
        if (metadata == null) {
            metadata = new BoltNeo4jDatabaseMetaData(this);
        }
        return metadata;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        this.checkClosed();
        if (this.transaction != null && this.transaction.isOpen()) {
            throw new SQLException("Method can't be called during a transaction");
        } else {
            super.doSetReadOnly(readOnly);
            if (this.session != null && this.session.isOpen()) {
                this.session.close();
                this.initSession();
            }

        }
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        if (this.autoCommit != autoCommit) {
            this.checkClosed();
            this.doCommit();
            this.autoCommit = autoCommit;
        }

    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        this.checkClosed();
        return this.autoCommit;
    }

    @Override
    public void commit() throws SQLException {
        this.checkClosed();
        this.checkAutoCommit();
        this.doCommit();
    }

    @Override
    public void doCommit() throws SQLException {
        if (this.transaction != null && this.transaction.isOpen()) {
            this.transaction.success();
            this.transaction.close();
            this.transaction = null;
            this.setClientInfo("bookmark", this.session.lastBookmark());
        }

    }

    @Override
    public void rollback() throws SQLException {
        this.checkClosed();
        this.checkAutoCommit();
        this.doRollback();
    }

    @Override
    public void doRollback() {
        if (this.transaction != null && this.transaction.isOpen()) {
            this.transaction.failure();
            this.transaction.close();
            this.transaction = null;
        }

    }

    @Override
    public Statement createStatement() throws SQLException {
        return this.createStatement(1003, 1007, 2);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return this.createStatement(resultSetType, resultSetConcurrency, 2);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        this.checkClosed();
        this.checkTypeParams(resultSetType);
        this.checkConcurrencyParams(resultSetConcurrency);
        this.checkHoldabilityParams(resultSetHoldability);
        this.initTransaction();
        return BoltNeo4jStatement
                .newInstance(false, this, new int[]{resultSetType, resultSetConcurrency, resultSetHoldability});
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return this.prepareStatement(this.nativeSQL(sql), 1003, 1007, 2);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        return this.prepareStatement(this.nativeSQL(sql), resultSetType, resultSetConcurrency, 2);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                              int resultSetHoldability) throws SQLException {
        this.checkClosed();
        this.checkTypeParams(resultSetType);
        this.checkConcurrencyParams(resultSetConcurrency);
        this.checkHoldabilityParams(resultSetHoldability);
        this.initTransaction();
        return BoltNeo4jPreparedStatement.newInstance(false, this, this.nativeSQL(sql),
                                                      new int[]{resultSetType, resultSetConcurrency,
                                                              resultSetHoldability});
    }

    @Override
    public boolean isClosed() throws SQLException {
        return this.driver == null || this.session != null && !this.session.isOpen();
    }

    @Override
    public void close() throws SQLException {
        try {
            if (!this.isClosed()) {
                if (this.transaction != null) {
                    this.transaction.close();
                    this.transaction = null;
                }

                if (this.session != null) {
                    this.session.close();
                    this.session = null;
                    this.driver.close();
                    this.driver = null;
                }
            }

        } catch (Exception var2) {
            throw new SQLException("A database access error has occurred: " + var2.getMessage());
        }
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        if (timeout < 0) {
            throw new SQLException("Timeout can't be less than zero");
        } else if (this.isClosed()) {
            return false;
        } else {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Session s = BoltNeo4jConnectionImpl.this.getSession();
                    Transaction tr = BoltNeo4jConnectionImpl.this.getTransaction();
                    if (tr != null && tr.isOpen()) {
                        tr.run("RETURN 1");
                    } else {
                        s.run("RETURN 1");
                    }

                }
            };

            try {
                TimeLimitedCodeBlock.runWithTimeout(r, (long) timeout, TimeUnit.SECONDS);
                return true;
            } catch (Exception var4) {
                LOGGER.log(Level.FINEST, "Catch exception totally fine", var4);
                return false;
            }
        }
    }

    private void initSession() {
        try {
            String bookmark = this.getClientInfo("bookmark");
            this.session = this.driver.session(this.getReadOnly() ? AccessMode.READ : AccessMode.WRITE, bookmark);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    private void initTransaction() {
        try {
            if (this.transaction == null) {
                this.transaction = this.session.beginTransaction();
            } else if (this.getAutoCommit()) {
                if (this.transaction.isOpen()) {
                    this.transaction.success();
                    this.transaction.close();
                    this.setClientInfo("bookmark", this.session.lastBookmark());
                }

                this.transaction = this.session.beginTransaction();
            }

        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }
}

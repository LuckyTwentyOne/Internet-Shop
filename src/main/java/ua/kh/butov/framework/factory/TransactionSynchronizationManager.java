package ua.kh.butov.framework.factory;

import java.util.LinkedList;
import java.util.List;

import ua.kh.butov.framework.FrameworkSystemException;

public class TransactionSynchronizationManager {
	private static final ThreadLocal<List<TransactionSynchronization>> transactionSynchronizations = new ThreadLocal<>();

	public static void addSynchronization(TransactionSynchronization transactionSynchronization) {
		List<TransactionSynchronization> list = getSynchronizations();
		if (list == null) {
			throw new FrameworkSystemException(
					"transactionSynchronizations is null. Does your service method have @Transactional(readOnly=false) annotation?");
		}
		list.add(transactionSynchronization);
	}

	static void initSynchronization() {
		transactionSynchronizations.set(new LinkedList<>());
	}

	static List<TransactionSynchronization> getSynchronizations() {
		return transactionSynchronizations.get();
	}

	static void clearSynchronization() {
		transactionSynchronizations.remove();
	}
}

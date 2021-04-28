package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("LibrarianView") == true) {
			return new LibrarianView(model);
		}
		else if(viewName.equals("AddWorkerView") == true)
		{
			return new AddWorkerView(model);
		}
		else if(viewName.equals("AddStudentBorrowerView") == true)
		{
			return new AddStudentBorrowerView(model);
		}
		else if(viewName.equals("AddBookView") == true)
		{
			return new AddBookView(model);
		}
		else if(viewName.equals("TransactionChoiceView") == true)
		{
			return new TransactionChoiceView(model);
		}
		else if(viewName.equals("WorkerBannerIdView") == true)
		{
			return new WorkerBannerIdView(model);
		}
		else if(viewName.equals("WorkerSelectionView") == true)
		{
			return new WorkerSelectionView(model);
		}
		else if(viewName.equals("ModifyWorkerView") == true)
		{
			return new ModifyWorkerView(model);
		}
		else if(viewName.equals("DeleteWorkerVerificationView") == true) {
			return new DeleteWorkerVerificationView(model);
		}
		else if(viewName.equals("StudentSearchNameView") == true)
		{
			return new StudentSearchNameView(model);
		}
		else if(viewName.equals("StudentSelectionView") == true)
		{
			return new StudentSelectionView(model);
		}
		else if(viewName.equals("StudentModificationView") == true)
		{
			return new StudentModificationView(model);
		}
		else if(viewName.equals("DeleteStudentBorrowerVerificationView") == true)
		{
			return new DeleteStudentBorrowerVerificationView(model);
		}
		else if(viewName.equals("DepositTransactionView") == true)
		{
			return new DepositTransactionView(model);
		}
		else if(viewName.equals("DepositAmountView") == true)
		{
			return new DepositAmountView(model);
		}
		else if(viewName.equals("WithdrawTransactionView") == true)
		{
			return new WithdrawTransactionView(model);
		}
		else if(viewName.equals("TransferTransactionView") == true)
		{
			return new TransferTransactionView(model);
		}
		else if(viewName.equals("BalanceInquiryTransactionView") == true)
		{
			return new BalanceInquiryTransactionView(model);
		}
		else if(viewName.equals("BalanceInquiryReceipt") == true)
		{
			return new BalanceInquiryReceipt(model);
		}
		else if(viewName.equals("WithdrawReceipt") == true)
		{
			return new WithdrawReceipt(model);
		}
		else if(viewName.equals("DepositReceipt") == true)
		{
			return new DepositReceipt(model);
		}
		else if(viewName.equals("TransferReceipt") == true)
		{
			return new TransferReceipt(model);
		}
		else if(viewName.equals("BarcodeSearchView") == true)
		{
			return new BookSubmitBarcodeScreen(model);
		}
		else if(viewName.equals("CheckoutBook") == true)
		{
			return new CheckOutBookScreenStudentSearchView(model);
		}
		else if(viewName.equals("CheckInBook") == true)
		{
			return new CheckInBookScreenStudentSearchView(model);
		}

		else
			return null;
	}


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	*/

}

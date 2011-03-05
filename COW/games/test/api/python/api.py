from com.python import PyApiCall
from jarray import array
from java.lang import Boolean
from java.lang import Integer
from java.lang import Double
from java.lang import String

#Phases
PHASE_TEST_BOOL = 1
PHASE_TEST_INT = 2
PHASE_TEST_DOUBLE = 3
PHASE_TEST_STRING = 4
PHASE_TEST_ARRAY = 5
PHASE_TEST_VIEW = 6
PHASE_TEST_TURN = 7

#API functions
API_XOR = 01
API_PLUS_PLUS = 11
API_DIVIDE_10 = 21
API_CONCAT = 31
API_SUM = 41
API_COUNT = 42
API_PRINT_TEXT = 51

def xor(a, b):
	call = PyApiCall(API_XOR, 2)
	call.addBool(a)
	call.addBool(b)
	return communicator.callGameApi(call)

def plusplus(x):
	call = PyApiCall(API_PLUS_PLUS, 1)
	call.addInt(x)
	return communicator.callGameApi(call)

def divide10(x):
	call = PyApiCall(API_DIVIDE_10, 1)
	call.addDouble(x)
	return communicator.callGameApi(call)

def concat(s1, s2):
	call = PyApiCall(API_CONCAT, 2)
	call.addString(s1)
	call.addString(s2)
	return communicator.callGameApi(call)

def sum(values):
	call = PyApiCall(API_SUM, 1)
	call.addIntArray(array(values, "i"))
	return communicator.callGameApi(call)

def count(nb):
	call = PyApiCall(API_COUNT, 1)
	call.addInt(nb)
	return communicator.callGameApi(call)

def printText(text):
	call = PyApiCall(API_PRINT_TEXT, 1)
	call.addString(text)
	return communicator.callGameApi(call)

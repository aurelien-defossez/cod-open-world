
class Ai(object):
	#Initialises the AI
	def init(self):
		#Do nothing
		nothing = 0
	
	#Executes the AI
	def execute(self, phase):
		#Phase: Test bool
		if phase == PHASE_TEST_BOOL:
			print "Test: F xor F =", xor(False, False)
			print "Test: F xor T =", xor(False, True)
			print "Test: T xor F =", xor(True, False)
			print "Test: T xor T =", xor(True, True)
			print "Test: 0 xor 1 =", xor(0, 1)
		
		#Phase : Test int
		elif phase == PHASE_TEST_INT:
			print "Test: 42++ =", plusplus(42)
			print "Test: -42++ =", plusplus(-42)
		
		#Phase : Test double
		elif phase == PHASE_TEST_DOUBLE:
			print "Test: 42/10 =", divide10(42)
			print "Test: -567/10 =", divide10(-567)
		
		#Phase : Test string
		elif phase == PHASE_TEST_STRING:
			print "Test: Hello.World =", concat("Hello", "World")
		
		#Phase : Test array
		elif phase == PHASE_TEST_ARRAY:
			print "Test: sum([3, 4, 5, 6, 7, 8, 9]) =", sum([3, 4, 5, 6, 7, 8, 9])
			values = count(10)
			print "Test: count(10) =", values
			print "Test: count(10)[7:] =", values[7:]
		
		#Phase : Test array
		elif phase == PHASE_TEST_VIEW:
			print "Test: printText('plop')"
			printText("plop")
	
	#Stops the AI
	def stop(self):
		#Do nothing
		nothing = 0

# Ruby interface for COMS W4444
# I don't like Java, neither should you
# Yufei Liu

def initialize(player_index, class_name, in_hand)
	
end

def happier(happiness_up)

end

def pick_offer(current_offers)

end

def update_offer(current_offers)

end

def offer_executed(offer_picked)

end

def sync_in_hand(in_hand)

end

def eat

end

def offer

end

input = gets.chomp
while input && input!="exit"
	puts input
	STDOUT.flush
	puts "--EOF--"
	STDOUT.flush
	input = gets.chomp
end

puts "bye!"
STDOUT.flush

#!/usr/bin/env python

import sys

if len(sys.argv) < 2:
    print sys.argv[0] + ' CardList.csv'
    sys.exit(0)

print 'package com.hearthsim.card;'

print 'import java.util.ArrayList;'
print 'import java.util.HashMap;'
print 'import java.util.Map;'


infile = open(sys.argv[1])
lines = infile.readlines()


print 'import com.hearthsim.card.minion.concrete.*;'
print 'import com.hearthsim.card.spellcard.concrete.*;'
print 'import com.hearthsim.card.weapon.concrete.*;'
    

print '/**'
print ' * A list of all implemented cards'
print ' *'
print ' * WARNING!!!!!'
print ' * This class is auto-generated by generateImplementedCardList.py.'
print ' * Do not modify directly.'
print ' *'
print ' **/'
print 'public class ImplementedCardList {'
print '    private ArrayList<ImplementedCard> list_;'
print '    private Map<Class<?>, ImplementedCard> map_;'
print '    public class ImplementedCard implements Comparable<ImplementedCard> {'
print ''
print '        public final Class cardClass_;'
print '        public final String name_;'
print '        public final String type_;'
print '        public final String charClass_;'
print '        public final String rarity_;'
print '        public final String text_;'
print '        public final int mana_;'
print '        public final int attack_;'
print '        public final int health_;'
print ''
print '        public ImplementedCard(Class cardClass, String name, String type, String charClass, String rarity, String text, int mana, int attack, int health) {'
print '            cardClass_ = cardClass;'
print '            name_ = name;'
print '            type_ = type;'
print '            charClass_ = charClass;'
print '            rarity_ = rarity;'
print '            text_ = text;'
print '            mana_ = mana;'
print '            attack_ = attack;'
print '            health_ = health;'
print '        }'
print
print '        @Override'
print '        public int compareTo(ImplementedCard o) {'
print '            int result = Integer.compare(this.mana_, o.mana_);'
print '            if (result == 0) {'
print '               result = this.name_.compareTo(o.name_);'
print '            }'
print '            return result;'
print '        }'
print
print '    }'
print
print '    public ImplementedCardList() {'
print '        list_ = new ArrayList<ImplementedCard>();'
print '        map_ = new HashMap<Class<?>, ImplementedCard>();'
print ''


#read in the data file
for line in lines:
    tokens = line.replace('\'','').replace('-','').replace(':','').strip().split("|")
    if tokens[0].lower() == 'name':
        continue
    name = tokens[0]
    className = tokens[0].title().replace(' ', '')
    ctype = tokens[1]
    charClass = tokens[2]
    rarity = tokens[3]
    mana = tokens[4]
    attack = tokens[5]
    health = tokens[6]
    text = tokens[7]
    implemented = tokens[8]

    if className == 'AlakirTheWindlord':
        className = 'AlAkirTheWindlord'

    if attack == '':
        attack = '-1'
    if health == '':
        health = '-1'
    
    if (implemented.lower() == 'true'):
        implemented = True
    else:
        implemented = False
    
    if implemented:
        print '        {'
        print '            ImplementedCard card = new ImplementedCard(%s.class, "%s", "%s", "%s", "%s", "%s", %s, %s, %s);' % (className, name, ctype, charClass, rarity, text, mana, attack, health)
        print '            list_.add(card);'
        print '            map_.put(%s.class, card);' % className
        print '        }'

print '    }'
print
print '    public ArrayList<ImplementedCard> getCardList() {'
print '        return list_;'
print '    }'
print 
print '    public ImplementedCard getCardForClass(Class<?> clazz) {'
print '        return map_.get(clazz);'
print '    }'


print '}'



    
    


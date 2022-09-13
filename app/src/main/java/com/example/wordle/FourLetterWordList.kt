package com.example.wordle

// author: calren
object FourLetterWordList {
    // List of most common 4 letter words from: https://7esl.com/4-letter-words/
    const val fourLetterWords =
        "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"

    // List of 4 letter animal words from: https://bestforpuzzles.com/lists/animals/4.html
    const val fourLetterAnimalWords =
        "Anoa,Asse,Bear,Boar,Buck,Bull,Calf,Cavy,Colt,Cony,Dauw,Deer,Dleb,Douc,Dzho,Euro,Eyra,Fawn,Foal,Gaur,Gilt,Goat,Guib,Gyal,Hare,Hart,Hind,Hogg,Ibex,Joey,Jomo,Kine,Kudu,Lamb,Lion,Lynx,Maki,Mara,Mare,Mico,Mink,Moco,Mohr,Moke,Mole,Mona,Mule,Musk,Napu,Neat,Nowt,Oont,Orca,Oryx,Oxen,Paca,Paco,Pard,Peba,Pika,Pudu,Puma,Quey,Roan,Runt,Rusa,Saki,Seal,Skug,Sore,Tait,Tegg,Titi,Unau,Urus,Urva,Vari,Vole,Wolf,Zati,Zobo,Zobu"

    // Returns a list of four letter words as a list
    fun getAllFourLetterWords(list: String): List<String> {
        return list.split(",")
    }

    // Returns a random four letter word from the list in all caps
    fun getRandomFourLetterWord(useStandard: Boolean): String {
        var allWords = getAllFourLetterWords(fourLetterWords)
        if (!useStandard) {
            allWords = getAllFourLetterWords(fourLetterAnimalWords)
        }
        // Creates a list, shuffling indices of chosen word list and grabs the last index element
        val randomNumber = (allWords.indices).shuffled().last()
        return allWords[randomNumber].uppercase()
    }
}
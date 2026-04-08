**Weaseley's Poker Night ♤ ♡ ♢ ♧**  
เกมไพ่โป๊กเกอร์ (Texas Hold'em) ในรูปแบบ Tournament สำหรับ Android ท้าทายทักษะการอ่านเกมและลุ้นดวงไพ่กับ AI สามตัวที่มีนิสัยการเล่นที่แตกต่างกัน เป็นคนสุดท้ายที่เหลือบนโต๊ะ  

***Tech Stack***  
+ Language: Kotlin  
+ UI Framework: Jetpack Compose  
+ Architecture: MVVM  
+ Local Storage: DataStore/Room  
+ State Management: StateFlow / LiveData
  
***Features***  
+ AI หลายบุคลิก : มีความสามารถในการคิดและประมวลผลไพ่ที่ถือและการเล่นของผู้เล่นคนอื่นๆ สามารถตัดสินใจหมอบหรือเดิมพัน  
  + AI เพิ่ม Personality ให้กับเกม : มีการพูดคุย ทำให้ต่างจากเกม Poker อื่นๆ  
+ ระบบ Tournament : ระบบ Blind โดย Big Blind จะเริ่มต้นที่ 200 และเพิ่มขึ้นทุกๆ 5 รอบ (200 incremental) เพื่อเพิ่มตัวแปรการตัดสินใจ
+ Customization : ผู้เล่นสามารถปรับแต่งจำนวนเงินเริ่มต้น, UI ของไพ่ให้มีหน้าตาที่อ่านง่ายหรือที่ตนเองชอบ หรือแต่งฉากหลัง, ลดเสียง ปิดการพูดคุยระหว่าง AI

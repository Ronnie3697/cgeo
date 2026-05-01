# c:geo Mapy.com edition 🥩

Osobní fork [c:geo](https://github.com/cgeo/cgeo) (Android geocaching klient) přizpůsobený **českým geocacherům**, kteří preferují **Mapy.com** (dříve Mapy.cz) místo Google Map.

> Tohle **NENÍ oficiální verze** c:geo. Je to kopie originálního zdrojového kódu s pár vlastními úpravami. Vše ostatní (geocaching.com login, OpenCaching, BRouter, offline mapy, logování keší...) funguje stejně jako v originále.

## 🔗 Originál

- **GitHub originálu**: https://github.com/cgeo/cgeo
- **Oficiální stránky**: https://www.cgeo.org/
- **Manuál**: https://manual.cgeo.org/
- **Veškeré zásluhy** patří [c:geo komunitě](https://github.com/cgeo/cgeo/graphs/contributors). Tahle edice je jen kopie s drobnou českou úpravou.

## ✨ Co je tu navíc oproti originálu

- 🗺️ **4 nové mapové vrstvy z Mapy.com**:
  - Mapy.com: Basic (běžná mapa)
  - Mapy.com: Outdoor (turistická, ideální pro geocaching)
  - Mapy.com: Aerial (letecká)
  - Mapy.com: Winter (zimní)
- ❌ **Bez Google Map** — debug build nemá Google Maps API klíč (originální release ho má, ale je vázaný na c:geo billing). Pro Čechy přínos minimální, máme Mapy.com a OpenStreetMap.
- 🥩 Vlastní ikonka rib eye steaku, ať appku v launcheri snadno najdeš.

## 📲 Instalace na telefon

### Direct download URL (bookmark si na mobilu)

```
https://github.com/Ronnie3697/cgeo/releases/latest/download/cgeo-mapycz-debug.apk
```

Tento URL ukazuje **vždy na poslední build** — když přijde upstream update, automaticky se přepíše a stačí stáhnout znova.

### Postup

1. Otevři URL výše v browseru na telefonu (Chrome / Firefox / cokoliv)
2. Klepni na stažený soubor v notifikacích nebo v Downloads
3. Pokud Android nahlásí "neznámý zdroj":
   - Nastavení → Apps → tvůj browser → **Install unknown apps** → Allow
   - Vrať se a klepni Install znova
4. Hotovo. Appka se nainstaluje **vedle** originálního c:geo (jiný package `cgeo.geocaching.developer`, jiná ikonka).

## 🔑 Jak získat Mapy.com API klíč

Mapy.com nejsou zdarma jako OpenStreetMap — potřebuješ vlastní klíč. **Free tier** pokrývá 250 000 dlaždic / měsíc, což je pro osobní použití více než dost (typicky vystačí na celý rok geocachování).

1. Otevři **https://developer.mapy.com**
2. Klepni **Sign in** v pravém horním rohu → vyber **Sign in with Seznam** → přihlas se svým seznam.cz e-mailem a heslem (případně si tam udělej účet, je zdarma)
3. V dashboardu klepni **Vytvořit aplikaci / Create application**
   - Název dej jakýkoliv (např. "c:geo personal")
   - Description nepovinné
   - **Restrikce nech prázdné** (žádné Referer / Origin / IP), jinak appka klíč nebude moct použít
4. Po vytvoření se ti zobrazí detail aplikace — zkopíruj **API klíč** (cca 30+ znaků)

## ⚙️ Jak vložit klíč do appky

1. Spusť **c:geo (developer)** (steak ikonka)
2. **3 tečky vpravo nahoře → Nastavení**
3. Někde na začátku najdi **"Rozšířená nastavení"** (Advanced settings) a **zapni** je. **Bez tohohle kroku položku níže neuvidíš!**
4. Vrať se v nastavení a otevři sekci **Mapová data** (Map data)
5. Najdi novou položku **"Použít Mapy.com"** a zaškrtni
6. Pod ní se rozsvítí pole **"Mapy.com API klíč"** → klepni → vlož klíč → OK
7. Vrať se a **force-stop appku**:
   - Nastavení Androidu → Apps → c:geo (developer) → Force stop
8. Otevři appku znova → otevři mapu (záložka Live mapa nebo Mapa kolem mě)
9. V mapě klepni na ikonku **vrstev** (levý horní roh, čtverečky / glóbus) → uvidíš:
   - **Mapy.com: Basic**
   - **Mapy.com: Outdoor** ← doporučená pro geocaching
   - **Mapy.com: Aerial**
   - **Mapy.com: Winter**

Vyber co se ti hodí. 🎉

## ❗ Co když mapy svítí šedě

- Špatný klíč nebo má restriction → vrať se na developer.mapy.com a ověř, že restriction je prázdné
- Vyčerpaná free kvóta (250k tiles/měsíc) → počkej do dalšího měsíce, nebo si zaplať vyšší tier
- Pomalý internet → počkej (tile server je mírně pomalejší než Google CDN)

## 🤖 Jak se udržuje aktuální

Repo automaticky každý den ve **3:00 UTC** (5:00 ČR letní čas / 4:00 zimní) sleduje upstream [cgeo/cgeo](https://github.com/cgeo/cgeo). Když přijdou nové commity:

1. **Auto-sync workflow** vytvoří PR `master → mapycz`
2. Po **merge** PR (manuálně) **build workflow automaticky postaví nové APK** a updatne release
3. Stačí znova stáhnout APK z release URL výše a přeinstalovat — data v appce zůstanou

## ⚖️ Licence

Stejně jako originální c:geo: [Apache License 2.0](LICENSE).

Veškerý code je z originálního c:geo repa. Změny v této edici jsou drobné (4 nové soubory pro Mapy.com providery + ~10 řádků v existujících souborech) a podléhají stejné licenci.

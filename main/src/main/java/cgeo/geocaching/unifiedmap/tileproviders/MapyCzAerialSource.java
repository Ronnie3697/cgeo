package cgeo.geocaching.unifiedmap.tileproviders;

import cgeo.geocaching.R;
import cgeo.geocaching.utils.LocalizationUtils;

class MapyCzAerialSource extends AbstractMapyCzSource {
    MapyCzAerialSource() {
        super(LocalizationUtils.getPlainString(R.string.map_source_mapycz_aerial), "aerial", 19);
    }
}

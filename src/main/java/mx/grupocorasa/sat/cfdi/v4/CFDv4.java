package mx.grupocorasa.sat.cfdi.v4;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import mx.grupocorasa.sat.common.CfdCommon;

import java.util.Map;

public abstract class CFDv4 extends CfdCommon {

    protected final ImmutableMap<String, String> PREFIXES = ImmutableMap.of("http://www.w3.org/2001/XMLSchema-instance", "xsi", "http://www.sat.gob.mx/cfd/4", "cfdi");

    protected final Map<String, String> localPrefixes = Maps.newHashMap(PREFIXES);

    @Override
    public Map<String, String> getLocalPrefixes() {
        return localPrefixes;
    }

    @Override
    protected String getDigestAlgorithm() {
        return "SHA256withRSA";
    }
}

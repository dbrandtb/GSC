function transformaSigsEnIce (sigs) {
    debug('transformaSigsEnIce() args:', arguments);
    var ice = {
        smap1: {
            CDIDEPER: sigs.smap1.cdper,
            CDPERSON: sigs.smap1.cdperson,
            CDUNIECO: sigs.smap1.CDUNIECO,
            ESTADO: 'W',
            FEFIN: sigs.smap1.fefin,
            FEINI: sigs.smap1.feini,
            FESOLICI: sigs.smap1.fesolici,
            NMPOLIZA: '',
            TRAMITE: _p30_flujo.ntramite,
            TIPOFLOT: _p30_smap1.tipoflot,
            //aux...
            cdramo: sigs.smap1.cdramo,
            diasValidos: '20',
            nmpoliza: '',
            ntramiteIn: _p30_flujo.ntramite,
            'parametros.pv_cdagrupa': null,
            'parametros.pv_cdasegur': null,
            'parametros.pv_cdestado': null,
            'parametros.pv_cdgrupo': null,
            'parametros.pv_cdplan': null,
            'parametros.pv_cdramo': sigs.smap1.cdramo,
            'parametros.pv_cdtipsit': 'XPOLX',
            'parametros.pv_cdunieco': sigs.smap1.CDUNIECO,
            'parametros.pv_dsgrupo': null,
            'parametros.pv_estado': 'W',
            'parametros.pv_fecharef': 'null',
            'parametros.pv_fefecsit': 'null',
            'parametros.pv_nmpoliza': '',
            'parametros.pv_nmsbsitext': null,
            'parametros.pv_nmsitaux': null,
            'parametros.pv_nmsituac': '-1',
            'parametros.pv_nmsituaext': null,
            'parametros.pv_nmsuplem': '0',
            //parametros.pv_otvalorXY
            'parametros.pv_status': 'V',
            'parametros.pv_swreduci': null
        },
        slist2: [],
        success: true,
        exito: true
    };
    
    //ice.smap1.aux...
    for (var att in sigs.smap1) {
        // a u x . ...
        //0 1 2 3 4
        if (att.slice(0, 4) === 'aux.') {
            ice.smap1[att] = sigs.smap1[att];
        }
    }
    
    //ice.smap1.parametros.pv_otvalor...
    for (var att in sigs.slist1[0]) {
        // p a r a m e t r o s . p v _ o t v a l o r ...
        //0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        if (att.slice(0, 21) === 'parametros.pv_otvalor') {
            ice.smap1[att] = sigs.slist1[0][att];
        }
    }
    
    for (var i = 0; i < sigs.slist1.length; i++) {
        var autoSigs = sigs.slist1[i];
        var autoIce  = {
            CDAGRUPA: '1',
            CDASEGUR: '30',
            CDESTADO: '0',
            CDGRUPO: '',
            CDPLAN: autoSigs.cdplan,
            CDRAMO: autoSigs.cdramo,
            CDTIPSIT: autoSigs.cdtipsit,
            CDUNIECO: autoSigs.cdunieco,
            DSGRUPO: '',
            ESTADO: 'W',
            FECHAREF: autoSigs.feini,
            FEFECSIT: autoSigs.feini,
            NMPOLIZA: '',
            NMSBSITEXT: '',
            NMSITAUX: '1',
            NMSITUAC: autoSigs.nmsituac,
            NMSITUAEXT: '',
            NMSUPLEM: '0',
            STATUS: 'V',
            SWREDUCI: '',
            cdplan: autoSigs.cdplan,
            cdtipsit: autoSigs.cdtipsit,
            nmsituac: autoSigs.nmsituac
            //parametros.pv_otvalor...
        };
        for (var att in autoSigs) {
            // p a r a m e t r o s . p v _ o t v a l o r ...
            //0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
            if (att.slice(0, 21) === 'parametros.pv_otvalor') {
                autoIce[att] = autoSigs[att];
            }
        }
        ice.slist2.push(autoIce);
    }
    
    debug('transformaSigsEnIce ice:', ice);
    return ice;
}
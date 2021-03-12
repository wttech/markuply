import GraalBridgeReact from '@wttech/graal-bridge-react';

import Hello from '@/test/Hello';
import '../css/app';

const templating = new GraalBridgeReact();
templating.registerReact('hello', Hello);
templating.expose();

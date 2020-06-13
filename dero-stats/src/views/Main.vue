<template>
  <div id="main">
      <div class="first-line">
        <v-card class="elevation-5 infos" color="primary">
            <v-card-title class="center-text">
                <h2>Daemon Infos</h2>
            </v-card-title>
            <v-card-text>
                <ul>
                    <li><strong>Current Height:</strong> {{ formatValue(infos.height) }}</li>
                    <li><strong>Topo Height:</strong> {{ formatValue(infos.topoheight) }}</li>
                    <li><strong>Hashrate:</strong> {{ formatValue((infos.difficulty/(infos.target*1000*1000)).toFixed(2)) }} MH/s</li>
                    <li><strong>Average Block Time:</strong> {{ infos.averageblocktime50 }}s</li>
                    <li><strong>Difficulty:</strong> {{ formatValue(infos.difficulty) }} </li>
                    <li><strong>Median Block Size:</strong> {{ formatValue(infos.median_block_size/1000) }} kB</li>
                    <li><strong>Current Supply:</strong> {{ formatValue(infos.total_supply) }} DERO</li>
                </ul>
            </v-card-text>
        </v-card>
        <v-card class="elevation-5 blocks" :loading="loading" color="primary">
            <v-card-title>
                <h2>Blocks Found</h2>
                <v-spacer></v-spacer>
                <v-text-field class="search" v-model="blocksSearch" append-icon="magnify" label="Search" single-line hide-details></v-text-field>
            </v-card-title>
            <v-data-table v-model="selected" show-select no-data-text="No blocks found" :search="blocksSearch" multi-sort :headers="blocksHeaders" :items="blocks" :items-per-page="5">
            </v-data-table>
        </v-card>
      </div>
      <v-card class="elevation-5 miners" :loading="loading" color="primary">
          <v-card-title>
              <h2>Connected Miners</h2>
              <v-spacer></v-spacer>
              <v-text-field class="search" v-model="minersSearch" no-data-text="No miners connected" append-icon="magnify" label="Search" single-line hide-details></v-text-field>
          </v-card-title>
          <v-data-table v-model="selected" show-select :search="minersSearch" multi-sort :headers="headers" :items="miners" :items-per-page="5">
              <template v-slot:item.lastJobRequest="{ item }">
                  <span>{{ formatTime(item.lastJobRequest) }}</span>
              </template>
              <template v-slot:item.lastBlockSent="{ item }">
                  <span>{{ item.lastBlockSent != -1 ? formatTime(item.lastBlockSent) : "No block found yet" }}</span>
              </template>
              <!--<template v-slot:item.alive="{ item }">
                  <span :style="'color:' + (item.alive ? 'green' : 'red')">{{ item.alive }}</span>
              </template>-->
          </v-data-table>
          <v-card-text v-show="!this.loading" style="text-align: center;"><strong>Currently mining on:</strong> {{ infos.walletAddress }}</v-card-text>
      </v-card>
  </div>
</template>

<script>
export default {
    data() {
        return {
            loading: true,
            minersSearch: "",
            blocksSearch: "",
            selected: [],
            headers: [
                {
                    text: "IP",
                    align: "start",
                    value: "ip"
                },
                {
                    text: "Last Job Request",
                    value: "lastJobRequest"
                },
                {
                    text: "Last Block Found",
                    value: "lastBlockSent"
                },
                {
                    text: "Blocks Found",
                    value: "blocksFound"
                },
                {
                    text: "Connected",
                    value: "alive"
                }
            ],
            blocksHeaders: [
                {
                    text: "Miner",
                    align: "start",
                    value: "minerIp"
                },
                {
                    text: "Block Height",
                    value: "height"
                },
                {
                    text: "Hash",
                    value: "blid"
                },
                {
                    text: "Status",
                    value: "status"
                }
            ],
            miners: [],
            infos: {},
            blocks: []
        }
    },
    mounted() {
        this.update()
        setInterval(() => this.update(), 5000)
    },
    methods: {
        update() {
            fetch(this.$api + "/info").then(result => result.json()).then(json => {
                this.infos = json
            })

            fetch(this.$api + "/blocks").then(result => result.json()).then(json => {
                json.reverse()
                this.blocks = json
            })

            fetch(this.$api + "/miners").then(result => result.json()).then(json => {
            if (this.loading) {
                this.loading = false
            }
            this.miners = json
        })
        },
        formatTime(timestamp) {
            let date = new Date(timestamp);
            return ("00" + (date.getMonth() + 1)).slice(-2) + "/" +
                   ("00" + date.getDate()).slice(-2) + "/" +
                   date.getFullYear() + " " +
                   ("00" + date.getHours()).slice(-2) + ":" +
                   ("00" + date.getMinutes()).slice(-2) + ":" +
                   ("00" + date.getSeconds()).slice(-2);
        },
        formatValue(value) {
            if (!value) return 0
          return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ")
        }
    }
}
</script>

<style scoped>
#main {
  margin: 15%;
  margin-top: 5%;
  margin-bottom: 5%;
}
.theme--dark.v-data-table {
    background-color: var(--v-primary-base);
}

.theme--light.v-data-table {
    background-color: var(--v-primary-base);
}

.first-line {
    display: flex;
    justify-items: center;
    align-items: flex-start;
}

.infos {
    width: 30%;
    text-align: left;
}

ul li {
    list-style: none;
    padding: 0;
    margin-bottom: 2%;
}

.blocks {
    margin: auto;
    margin-left: 5%;
    width: 100%;
    padding-left: 1%;
    padding-right: 1%;
}

.miners {
    margin-top: 5%;
    padding-left: 1%;
    padding-right: 1%;
    width: 100%;
}

.center-text {
    text-align: center;
}

@media screen and (max-width: 1280px)
{
    #main {
        margin: 5%;
    }

    .first-line {
        margin: auto;
        flex-direction: column;
    }

    .infos {
        margin: auto;
        width: auto;
    }

    .blocks {
        margin: auto;
        margin-top: 5%;
        margin-bottom: 5%;
    }
}
</style>